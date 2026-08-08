# Unified offline study data

The Android app uses a normalized data model so Bible text and study resources can come from different providers without coupling the UI to any one source.

## Import policy

1. Keep the provider/source ID on every imported record.
2. Keep exact license and attribution metadata.
3. Verify the exact file being bundled; repository-level licensing is not automatically sufficient for every file.
4. Do not bundle a Church Father translation merely because the ancient work itself is public domain; verify the edition/translation.
5. Do not present AI-generated historical or theological claims as primary sources. AI responses should link back to the underlying local records.

## Planned dataset layers

- `abidu-amharic`: Amharic Bible API/runtime source.
- `sblgnt`: Greek New Testament.
- `wlc`: Hebrew Old Testament.
- `stepbible`: lexical/Strong's/morphology support where the exact data file permits redistribution.
- `macula-greek`: Greek morphology/syntax/semantic annotation.
- `macula-hebrew`: Hebrew morphology/syntax/semantic annotation.
- `openbible-geocoding`: biblical places and coordinates.
- `patristics`: source-by-source public-domain or appropriately licensed editions.
- `historical`: source-by-source open and attributed historical datasets.

The import manifest is `registry/import-manifest.json`. The normalized record contract is `schema/study-data.schema.json`.
