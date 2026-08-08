# Offline database and search architecture

## Goal

Provide fast Android/offline search across Bible verses, Greek/Hebrew lexical records, people, places, events, historical/theological context, Church Fathers, and cross-references without requiring an internet connection.

## Recommended packaging

Use a local SQLite database as the runtime database. Keep source datasets in normalized JSON/TSV staging during ingestion, then compile approved records into SQLite with indexes.

## Logical tables

- bible_versions
- books
- verses
- lexical_entries
- verse_word_links
- people_events
- places
- study_context
- patristic_records
- cross_references
- sources
- search_records

## Search

Create SQLite indexes for verse references, normalized English text, Amharic text, lemmas, Strong's IDs, names, places and source IDs. Full-text search should be optional where the Android SQLite build supports FTS5; the core app must still work with ordinary indexed queries.

## Amharic search

Store original Amharic text and a normalized search form. Normalization must be deterministic and must not destroy the original text displayed to users. Search should support exact verse lookup and useful Amharic text lookup.

## Licensing

Only records whose source/license status has passed the import gate may enter the distributable offline database. Source IDs and attribution remain attached to records.

## AI boundary

The offline database contains curated/licensed source information. The AI layer may query this database and synthesize answers, but generated content is not written back as authoritative offline source data.

## Version comparison

1954 and አዲሱ መደበኛ remain separate `bible_versions` records and can share verse/lexical/context links without merging their text.
