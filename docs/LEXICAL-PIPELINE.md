# Greek/Hebrew lexical pipeline

## Goal

Connect every original-language token to a stable lexical record without making AI the source of the data.

## Pipeline

```text
Bible verse
  -> original-language token
  -> token/verse identifier
  -> lemma
  -> morphology
  -> Strong's identifier (when available)
  -> lexicon entries
  -> semantic/word-sense data
  -> verse-specific sense
  -> English lexical information
  -> Amharic lexical explanation
  -> historical/theological sources
```

## Candidate sources inspected

### Greek
- Clear-Bible/macula-greek: SBLGNT XML/TSV and MACULA annotations; repository contains word-sense resources and transformation mappings.
- morphgnt/sblgnt: MorphGNT/SBLGNT source.
- morphgnt/py-sblgnt: Python access tooling for MorphGNT/SBLGNT.
- eliranwong/SBLGNT-add-ons: Strong-number and gloss add-ons; source files must be license-checked before bundling.
- jtauber/greek-lemma-mappings: lemma mapping candidate; license must be verified.

### Hebrew
- Clear-Bible/macula-hebrew: WLC nodes/lowfat/TSV and MACULA Hebrew mappings.
- 0xvtl/openscriptures-morphhb: Open Scriptures Hebrew morphology candidate; exact source files/license must be verified.
- 0xvtl/openscriptures-HebrewLexicon: Hebrew lexicon candidate; exact files/license must be verified.

### Cross-language / lexical discovery
- STEPBible/STEPBible-Data: large research-data repository; individual files and source licenses must be checked before bundling.

## Linking rules

1. Prefer stable verse/token IDs over matching visible text.
2. Link token -> lemma -> Strong's/lexicon using explicit mappings.
3. Preserve one-to-many relationships; a token can have multiple lexical analyses or senses.
4. Never collapse a semantic range into one gloss.
5. Keep verse-specific sense separate from dictionary definitions.
6. Keep English and Amharic explanations as separate fields.
7. AI-generated Amharic explanations are never stored as canonical lexical source data.
8. Every imported record keeps source ID, source version/commit, attribution, and license metadata.

## Offline behavior

The lexical records, mappings, morphology and permitted English/Amharic explanatory material are designed to work offline after packaging/indexing. Network access is not required for lookup of bundled records.

## Import gate

No third-party text or lexicon is bundled merely because it is on GitHub. The importer must stop unless the exact file-level license and redistribution/derivative-work rights are recorded in the source registry.
