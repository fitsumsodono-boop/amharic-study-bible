# Lexical import pipeline

## Flow

`approved source files -> staging -> normalization -> validation -> lexical index -> verse links`

### Normalization

Every record is mapped to the common lexical schema with:

- original-language lemma
- transliteration/pronunciation when available
- Strong's identifiers when available
- morphology and parsing
- English lexical meanings
- Amharic lexical explanation
- semantic range
- verse-specific senses
- source IDs and attribution

### Source adapters

Create one adapter per source rather than coupling the app to a single database format. Candidate adapters include MACULA Greek/Hebrew, MorphGNT, SBLGNT add-ons, Open Scriptures Hebrew resources, and other approved datasets.

### License gate

A source is eligible for import only after its exact files have verified licensing/provenance metadata in `data/registry/`. Public availability on GitHub is not sufficient permission to redistribute.

### Amharic requirement

Every normalized lexical entry must contain an Amharic explanation field. If an upstream dataset does not provide Amharic, a separate permitted translation/curation process must create it; the importer must not silently label an English gloss as an original Amharic source.

### Context requirement

Lexical entries must distinguish dictionary meaning from verse-specific sense. A Strong's number or gloss alone must never be presented as the complete meaning of a word in a verse.
