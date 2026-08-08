#!/usr/bin/env node

/** Build the distributable SQLite database from approved normalized staging data. */
import fs from 'node:fs/promises';
import path from 'node:path';
import { spawnSync } from 'node:child_process';

const root = process.cwd();
const schema = path.join(root, 'db', 'schema.sql');
const out = path.join(root, 'build', 'amharic-study-bible.sqlite');
const staging = path.join(root, 'data', 'staging');

async function exists(p) { try { await fs.access(p); return true; } catch { return false; } }

async function main() {
  if (!await exists(schema)) throw new Error('Missing db/schema.sql');
  if (!await exists(staging)) console.warn('No staging directory found; creating an empty database schema.');
  await fs.mkdir(path.dirname(out), { recursive: true });
  const sql = await fs.readFile(schema, 'utf8');
  const result = spawnSync('sqlite3', [out], { input: sql, encoding: 'utf8' });
  if (result.error) throw new Error(`sqlite3 is required to build the database: ${result.error.message}`);
  if (result.status !== 0) throw new Error(result.stderr || 'SQLite schema build failed');
  console.log(`Created ${path.relative(root, out)}`);
}
main().catch(e => { console.error(e.message); process.exit(1); });
