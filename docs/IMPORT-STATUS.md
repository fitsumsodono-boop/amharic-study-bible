# Bible text import status

## Requested editions

- 1954 / older Amharic Bible: registered, but full-text redistribution rights still require verification.
- አዲሱ መደበኛ ትርጉም (New Amharic Standard Version): registered, but full-text redistribution requires appropriate permission/license.

## What is saved in this repository

The repository contains the database schema, Android data layer, version registry, import templates, source registries, and licensing/provenance rules.

## What is intentionally NOT saved

No complete copyrighted Bible translation has been copied into this repository without verified redistribution rights.

## How to add an authorized Bible file

Place an authorized source under:

`data/staging/bible/<version-id>/`

Then normalize it to records containing:

- versionId
- book
- chapter
- verse
- text
- sourceId
- license

After validation, the records can be compiled into `build/amharic-study-bible.sqlite`.

## Principle

Do not label a generic Amharic Bible dataset as 1954 or አዲሱ መደበኛ unless the edition is independently verified. Do not scrape or redistribute a complete translation merely because a copy is available online.
