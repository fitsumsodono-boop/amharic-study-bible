# Verse-to-word linking

The study app needs a stable bridge from a Bible verse to each original-language token and then to the normalized lexical record.

## Data flow

`Bible version + verse -> verse-word link -> lemma -> Strong's -> morphology/syntax -> lexical entry`

Each link records the verse ID, original token, language, lemma, optional Strong's IDs, lexical-entry ID, position, and source.

## Why this is separate

A Bible translation and an original-language dataset are different sources. The linking layer connects them without replacing either source. This also allows multiple Amharic translations (for example 1954 and አዲሱ መደበኛ) to point to the same original-language verse while remaining separate translations.

## User experience

When a user taps a word, the app should show:

1. Original token
2. Lemma and transliteration
3. Morphological parsing
4. Strong's number when available
5. English lexical information
6. 🇪🇹 Amharic lexical explanation
7. Semantic range
8. Sense in this verse
9. Other occurrences
10. Relevant historical/theological notes
11. Church Father material when available
12. Source and license attribution

The UI must make clear which statements are source data and which are AI-generated synthesis.
