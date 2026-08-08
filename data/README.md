# Unified offline study data

This directory defines the release data pipeline for the Amharic Study Bible.

## Release rule

Only datasets with verified redistribution rights may be shipped in `android/app/src/main/assets/study/`. Runtime API data may be cached locally according to its provider's terms, but must not be silently republished as bundled data.

## Current source plan

- `abidu-amharic`: Amharic Bible runtime/cache source.
- `sblgnt`: Greek New Testament, CC BY 4.0.
- `wlc`: Hebrew Old Testament, public-domain source.
- `stepbible`: lexical/Strong's/morphology data, file-by-file license verification required.
- `macula-greek`: Greek morphology/syntax/semantic data, CC BY 4.0.
- `macula-hebrew`: Hebrew morphology/syntax/semantic data, CC BY 4.0.
- `openbible-geocoding`: biblical place data, subject to the dataset's stated CC BY 4.0 terms.
- `patristics`: source-by-source verification required.
- `historical`: source-by-source verification required.

## Normalized import format

Each asset is a JSON document with:

```json
{
  "source": {
    "sourceId": "...",
    "name": "...",
    "role": "...",
    "license": "...",
    "attribution": "...",
    "sourceUrl": "...",
    "verified": true
  },
  "records": []
}
```

The Android importer refuses documents where `verified` is false.

## Attribution

The app should expose source/license information in Settings > Licenses and on relevant study-data screens. Never remove upstream notices from imported source packages.

The import manifest is `registry/import-manifest.json`; the normalized record contract is `schema/study-data.schema.json`.
