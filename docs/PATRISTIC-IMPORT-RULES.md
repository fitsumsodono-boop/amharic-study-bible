# Patristic and historical import rules

## First approved candidate

`HistoricalChristianFaith/Commentaries-Database` is registered as a discovery/import candidate. Its README documents verse-linked TOML commentary records, author metadata, dates, source URLs, and compilation to SQLite/JSON/CSV. It also explicitly warns about attribution problems when a Father quotes a heretical source, so provenance must be preserved.

## Import policy

1. Verify the license of the repository and the exact underlying quotation/edition before redistribution.
2. Preserve author, work, date, source URL, and attribution.
3. Do not treat a quotation of another writer as the Father's own view.
4. Preserve disputed/pseudonymous attribution instead of silently correcting it.
5. Store original/source-language material separately from our Amharic translation or explanation.
6. Amharic explanations must be clearly labeled as translation, summary, or editorial explanation—not presented as an original patristic quotation.
7. AI-generated explanations remain outside the offline source database.

## Initial priority Fathers

Start with verse-linked material from major early and later interpreters, prioritizing coverage and provenance over volume. Suggested first-pass names include Augustine of Hippo, John Chrysostom, Jerome, Basil of Caesarea, Origen, Athanasius, Cyril of Alexandria, and Gregory of Nazianzus, subject to source and attribution verification.

## Output

The normalized record must conform to `data/schema/patristic-verse-link.schema.json` and include Amharic explanation fields plus source/license metadata.
