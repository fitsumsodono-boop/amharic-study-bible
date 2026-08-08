# Amharic 1954 candidate import

Source: `beldados/EthiopicBibleAPI`

The upstream source contains individual Amharic Bible books under `Books/*.json`. This directory is reserved for the normalized 66-book staging output.

Status: **candidate / edition identified provisionally; redistribution rights not yet verified**.

Do not ship this data in a public APK until the underlying Bible-text rights are verified. Do not rename this source to `am-1954` as a final legal designation until provenance and rights are confirmed.

Expected normalized record fields:

- `id`
- `versionId: am-1954-candidate`
- `book`
- `chapter`
- `verse`
- `text`
- `sourceId: ethiopic-bible-api`
- `licenseStatus`
