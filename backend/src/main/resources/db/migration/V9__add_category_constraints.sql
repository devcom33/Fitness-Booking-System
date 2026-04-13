ALTER TABLE fitness_class
    ADD CONSTRAINT fk_fitness_class_category
        FOREIGN KEY (category_id)
            REFERENCES category(id);

ALTER TABLE fitness_class
    ALTER COLUMN category_id SET NOT NULL;