# EthiopicBibleAPI integration

The uploaded `EthiopicBibleAPI` snapshot is now the project's planned Amharic import source.

Upstream: `beldados/EthiopicBibleAPI`

The snapshot contains 66 JSON book files (~5.7 MB uncompressed) with this structure:

`book -> chapters -> verses`

The upstream project is MIT-licensed, but the MIT license covers the project code, not automatically every underlying Bible text. Therefore the app tracks this as `edition-not-yet-verified` until the actual translation/source provenance is established.

## Integration

Use `scripts/import-ethiopic-bible-api.py` to convert the upstream `Books/` directory into normalized JSONL staging records. The importer preserves the source ID and does not falsely classify the text as 1954 or አዲሱ መደበኛ.

Example:

`python scripts/import-ethiopic-bible-api.py /path/to/EthiopicBibleAPI/Books data/staging/bible`

## Why this is useful

This gives the Android app a concrete Amharic verse source now while keeping the edition/licensing gate explicit. Once the edition is verified, change the version ID from the unverified source to the correct registered edition and compile it into SQLite.

## API vs offline database

The FastAPI service can remain an online API/reference implementation. The Android app should import approved JSON into SQLite so Bible reading and search do not require the API or internet.
