#!/usr/bin/env node

/**
 * Build a deterministic verse-to-word index from approved staged links.
 * No third-party data is downloaded or bundled by this script.
 */
import fs from 'node:fs/promises';
import path from 'node:path';

const root = process.cwd();
const inputDir = path.join(root, 'data', 'staging', 'verse-links');
const outputDir = path.join(root, 'data', 'index');
const outputFile = path.join(outputDir, 'verse-word-index.json');

async function main() {
  let files;
  try {
    files = (await fs.readdir(inputDir)).filter(f => f.endsWith('.json')).sort();
  } catch {
    console.log('No staged verse-word links found. Nothing to build.');
    return;
  }

  const links = [];
  for (const file of files) {
    const parsed = JSON.parse(await fs.readFile(path.join(inputDir, file), 'utf8'));
    for (const link of Array.isArray(parsed) ? parsed : [parsed]) {
      for (const field of ['id', 'verseId', 'token', 'language']) {
        if (!link[field]) throw new Error(`${file}: missing required field ${field}`);
      }
      if (link.lexicalEntryId && !link.lemma) {
        throw new Error(`${file}: lexicalEntryId requires lemma`);
      }
      links.push(link);
    }
  }

  links.sort((a, b) => a.verseId.localeCompare(b.verseId) || (a.position ?? 0) - (b.position ?? 0));
  await fs.mkdir(outputDir, { recursive: true });
  await fs.writeFile(outputFile, JSON.stringify({ version: 1, links }, null, 2) + '\n', 'utf8');
  console.log(`Built ${links.length} verse-word links: ${path.relative(root, outputFile)}`);
}

main().catch(err => { console.error(err.message); process.exit(1); });
