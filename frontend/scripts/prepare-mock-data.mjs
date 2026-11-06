import { readFile, mkdir, writeFile } from 'node:fs/promises';

// Angular serve asset solo dal workspace frontend: questa è una copia generata,
// ignorata da Git. La fonte rimane la cartella mock-data nella radice del repository.
const source = new URL('../../mock-data/', import.meta.url);
const destination = new URL('../.mock-data/', import.meta.url);
const files = ['brands.json', 'cigarettes.json'];

// Leggere e verificare entrambi i file prima di aggiornare la copia locale.
const fixtures = await Promise.all(files.map(async (file) => {
  const content = await readFile(new URL(file, source), 'utf8');
  if (!Array.isArray(JSON.parse(content))) {
    throw new Error(`La fixture ${file} deve contenere un array JSON.`);
  }
  return { file, content };
}));
await mkdir(destination, { recursive: true });
for (const { file, content } of fixtures) {
  await writeFile(new URL(file, destination), content);
}
console.log('Fixture collegate: mock-data/brands.json e mock-data/cigarettes.json.');
