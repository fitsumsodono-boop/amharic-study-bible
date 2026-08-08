#!/usr/bin/env node

/**
 * Build a deterministic normalized lexical index from approved source records.
 *
 * This script intentionally does NOT download or bundle third-party datasets.
 * Importers should first verify source/license metadata and write normalized
 * records to an approved staging directory.
 */

import fs from 'node:fs/promises';
import path from 'node:path';

const root = process.cwd();
const inputDir = path.join(root, 'data', 'staging', 'lexical');
const outputDir = path.join(root, 'data', 'index');
const outputFile = path.join(outputDir, 'lexical-index.json');

async function readJson(file) {
  return JSON.parse(await fs.readFile(file, 'utf8'));
}

async function main() {
  let names;
  try {
    names = (await fs.readdir(inputDir)).filter((n) => n.endsWith('.json')).sort();
  } catch {
    console.log('No staged lexical records found. Nothing to build.');
    return;
  }

  const entries = [];
  for (const name of names) {
    const value = await readJson(path.join(inputDir, name));
    const records = Array.isArray(value) ? value : [value];
    for (const record of records) {
      if (!record.id || !record.language || !record.lemma) {
        throw new Error(`Invalid lexical record in ${name}: id, language and lemma are required`);
      }
      if (!record.meanings?.english || !record.meanings?.amharic) {
        throw new Error(`Lexical record ${record.id} must contain English and Amharic meanings`);
      }
      entries.push(record);
    }
  }

  entries.sort((a, b) => `${a.language}:${a.lemma}:${a.id}`.localeCompare(`${b.language}:${b.lemma}:${b.id}`));

  await fs.mkdir(outputDir, { recursive: true });
  await fs.writeFile(outputFile, JSON.stringify({ version: 1, entries }, null, 2) + '\n', 'utf8');
  console.log(`Built ${entries.length} lexical entries: ${path.relative(root, outputFile)}`);
}

main().catch((error) => {
  console.error(error.message);
  process.exit(1);
});
