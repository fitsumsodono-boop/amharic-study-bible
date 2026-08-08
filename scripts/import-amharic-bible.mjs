#!/usr/bin/env node
/**
 * Build-time importer for Misikirayu/amharic-bible-api.
 *
 * IMPORTANT: this script does not copy the upstream dataset into this repo by
 * default. It fetches the public repository, validates its structure, and
 * writes a normalized local build artifact only when --out is supplied.
 * Review the upstream Bible-text provenance/licence before distributing that
 * artifact with the application.
 */
import fs from 'node:fs/promises';

const OWNER = 'Misikirayu';
const REPO = 'amharic-bible-api';
const BRANCH = 'main';
const BOOKS_PATH = 'data/books';

const args = process.argv.slice(2);
const outIndex = args.indexOf('--out');
const outPath = outIndex >= 0 ? args[outIndex + 1] : null;

async function getJson(url) {
  const response = await fetch(url, {
    headers: { 'Accept': 'application/vnd.github+json' }
  });
  if (!response.ok) {
    throw new Error(`GitHub request failed: ${response.status} ${response.statusText}`);
  }
  return response.json();
}

function normalizeBookId(name) {
  return name
    .replace(/\.json$/u, '')
    .normalize('NFC')
    .trim()
    .toLowerCase()
    .replace(/\s+/gu, '-')
    .replace(/[^\p{L}\p{N}_-]/gu, '-');
}

function normalizeBook(book) {
  if (!book || typeof book !== 'object') throw new Error('Book JSON is not an object');
  if (!Array.isArray(book.chapters)) throw new Error(`Invalid chapters in ${book.title ?? 'unknown book'}`);

  return {
    id: normalizeBookId(book.title),
    name: book.title,
    nameAm: book.title,
    abbreviation: book.abbv ?? '',
    testament: null,
    chapters: book.chapters.map((chapter, chapterIndex) => {
      if (!Array.isArray(chapter.verses)) {
        throw new Error(`Invalid verses in ${book.title}, chapter ${chapterIndex + 1}`);
      }
      return {
        number: Number(chapter.chapter ?? chapterIndex + 1),
        verses: chapter.verses.map((text, verseIndex) => ({
          number: verseIndex + 1,
          text: String(text)
        }))
      };
    })
  };
}

const apiRoot = `https://api.github.com/repos/${OWNER}/${REPO}`;
const entries = await getJson(`${apiRoot}/contents/${BOOKS_PATH}?ref=${BRANCH}`);
const bookEntries = entries.filter((entry) => entry.type === 'file' && entry.name.endsWith('.json'));

if (bookEntries.length === 0) throw new Error('No book JSON files found.');

const books = [];
for (const entry of bookEntries) {
  const source = await getJson(`${apiRoot}/contents/${encodeURIComponent(entry.path).replace(/%2F/gu, '/')}?ref=${BRANCH}`);
  const raw = Buffer.from(source.content.replace(/\n/gu, ''), 'base64').toString('utf8');
  books.push(normalizeBook(JSON.parse(raw)));
}

books.sort((a, b) => a.name.localeCompare(b.name, 'am'));
const result = {
  schemaVersion: 1,
  source: {
    repository: `${OWNER}/${REPO}`,
    ref: BRANCH,
    sourcePath: BOOKS_PATH
  },
  books
};

console.log(`Validated ${books.length} book files.`);
console.log(`Total chapters: ${books.reduce((n, b) => n + b.chapters.length, 0)}`);
console.log(`Total verses: ${books.reduce((n, b) => n + b.chapters.reduce((m, c) => m + c.verses.length, 0), 0)}`);

if (outPath) {
  await fs.mkdir(new URL('.', `file://${process.cwd()}/${outPath}`).pathname, { recursive: true }).catch(() => {});
  await fs.writeFile(outPath, JSON.stringify(result, null, 2) + '\n', 'utf8');
  console.log(`Wrote normalized dataset to ${outPath}`);
} else {
  console.log('Dry run only: no third-party Bible text was written to the repository.');
}
