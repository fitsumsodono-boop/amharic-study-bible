#!/usr/bin/env python3
"""Import EthiopicBibleAPI JSON books into normalized staging.

The source remains identified neutrally. Edition/licensing must be verified
before the data is shipped as 1954 or አዲሱ መደበኛ.
"""
import argparse, json, re
from pathlib import Path

SOURCE_ID = "ethiopic-bible-api"

def normalize(s: str) -> str:
    return re.sub(r"\s+", " ", s or "").strip()

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("books_dir", type=Path)
    ap.add_argument("output_dir", type=Path)
    ap.add_argument("--version-id", default="am-1954-candidate")
    args = ap.parse_args()
    args.output_dir.mkdir(parents=True, exist_ok=True)
    out = args.output_dir / f"{args.version_id}.jsonl"
    count = 0
    with out.open("w", encoding="utf-8") as f:
        for path in sorted(args.books_dir.glob("*.json")):
            data = json.loads(path.read_text(encoding="utf-8"))
            book = data.get("title", path.stem)
            for ch in data.get("chapters", []):
                chapter = int(ch["chapter"])
                for verse_no, text in enumerate(ch.get("verses", []), start=1):
                    if not text or text == "-":
                        continue
                    record = {
                        "id": f"{args.version_id}-{path.stem}-{chapter}-{verse_no}",
                        "versionId": args.version_id,
                        "book": book,
                        "chapter": chapter,
                        "verse": verse_no,
                        "text": text,
                        "normalizedText": normalize(text),
                        "sourceId": SOURCE_ID,
                        "editionStatus": "candidate"
                    }
                    f.write(json.dumps(record, ensure_ascii=False) + "\n")
                    count += 1
    print(f"Imported {count} verses from {args.books_dir} -> {out}")

if __name__ == "__main__":
    main()
