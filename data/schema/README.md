# Data schema

The schemas in this directory define the normalized format used by the offline source layer.

## Design

- Bible records are verse-addressable and preserve book/chapter/verse hierarchy.
- External scholarly resources are represented as source records and linked through stable IDs.
- Source records retain provenance, attribution, and license metadata.
- AI output is not stored as canonical source data.

## Import rule

Do not commit a third-party dataset merely because it is publicly downloadable. First add it to `data/registry/sources.json` with verified license/provenance information, then write an importer that maps it into these schemas.
