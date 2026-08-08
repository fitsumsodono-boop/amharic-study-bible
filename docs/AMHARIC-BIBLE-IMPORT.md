# Amharic Bible import status

## Upstream inspected

Repository: `Misikirayu/amharic-bible-api`

The upstream README describes the project as an Open Amharic Bible API with the full Amharic Bible, one JSON file per book, and a `data/books/` directory. Its README states that the repository is MIT licensed, but the README does not identify the provenance/license of the underlying Bible text separately.

## Decision

The Amharic Bible is **not yet bundled** into this repository. The project license and the underlying Scripture text's redistribution rights are different questions and must both be verified.

## Importer

`scripts/import-amharic-bible.mjs` performs a build-time fetch and structural validation. By default it is a dry run and does not write third-party text into this repository.

When redistribution rights are verified, the importer can be used to produce the normalized dataset described by `data/schema/bible.schema.json`.

## Validation goals

- confirm all expected books are present;
- validate JSON structure;
- validate chapter and verse numbering;
- preserve Amharic Unicode text;
- normalize stable book IDs;
- record source repository/ref;
- record license/provenance before distribution.

## Current limitation

The upstream repository README claims a full Bible, but the source-text provenance was not established by the README alone. Treat this dataset as **pending verification**, not as a cleared redistribution source.
