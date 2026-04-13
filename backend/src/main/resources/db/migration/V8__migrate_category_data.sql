UPDATE fitness_class fc
SET category_id = c.id
    FROM category c
WHERE LOWER(fc.category) = LOWER(c.name);

UPDATE fitness_class
SET category_id = (
    SELECT id FROM category WHERE name = 'Functional Training'
)
WHERE category_id IS NULL;