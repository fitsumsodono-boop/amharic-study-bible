# Amharic Study Bible — Architecture

## Vision

A source-grounded Amharic Bible study platform. Offline scholarly sources are stored and indexed separately from the AI layer.

## Core principles

1. Scripture is stored separately from commentary and research data.
2. Every external dataset keeps its original attribution and license metadata.
3. AI is an interpretation/synthesis layer, never the source of truth.
4. AI answers must cite the local source records used to generate them.
5. Original-language data is linked to verses through stable identifiers.
6. Amharic explanations distinguish textual evidence, lexical data, historical interpretation, patristic interpretation, and AI synthesis.
7. Copyright/licensing is checked before importing any dataset.

## Layers

### Offline source layer
- Amharic Bible
- Greek New Testament
- Hebrew Old Testament
- Greek/Hebrew morphology and syntax
- Strong's and other lexicons
- Study notes/commentary where license permits
- Church Fathers / historical Christian texts where license permits
- Biblical geography
- Historical and archaeological reference data
- Textual criticism/manuscript data where license permits
- Ethiopian/Ge'ez historical and manuscript metadata where license permits

### Research/index layer
- Verse index
- Word/lemma index
- Strong's index
- Cross-reference graph
- People/places/events entities
- Timeline
- Source and license registry
- Full-text search index

### AI layer (separate)
The AI layer receives retrieved source records and produces Amharic explanations. It must not silently invent source material or replace the offline database.

Suggested answer sections:
1. Verse
2. Immediate context
3. Original language
4. Word study
5. Historical/cultural background
6. Theology
7. Church Fathers
8. Cross references
9. Interpretive differences
10. Source-grounded Amharic synthesis

## Suggested repository layout

```text
app/                  Application code
api/                  API contracts and server code
packages/             Shared types/utilities
data/
  bible/              Scripture datasets
  languages/          Greek/Hebrew/Ge'ez language data
  lexicons/           Lexical datasets
  morphology/         Morphology/syntax datasets
  patristics/         Church Father texts and metadata
  commentary/         Licensed study notes
  history/            Historical/archaeological data
  geography/          Places/maps data
  textual/             Textual criticism data
  ethiopian/          Ethiopian/Ge'ez resources
  registry/            Source/license manifests
scripts/              Import/validation/indexing scripts
docs/                 Architecture and research documentation
tests/                Data and API tests
```

No raw third-party dataset should be added until its license and attribution are recorded in `data/registry/sources.json`.
