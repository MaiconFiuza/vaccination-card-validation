INSERT INTO vaccinations (id, name, date, type, user_id)
VALUES
    (gen_random_uuid(), 'Hepatite B', '2022-01-10', 'PRIMEIRA_DOSE', 'c0e9ad51-b0a1-4a9f-bec4-4a0c2dd1b7d6'),
    (gen_random_uuid(), 'Tétano', '1998-02-05', 'PRIMEIRA_DOSE', 'c0e9ad51-b0a1-4a9f-bec4-4a0c2dd1b7d6'),
    (gen_random_uuid(), 'COVID-19', '2021-03-20', 'PRIMEIRA_DOSE', 'c0e9ad51-b0a1-4a9f-bec4-4a0c2dd1b7d6'),
    (gen_random_uuid(), 'COVID-19', '2021-05-15', 'SEGUNDA_DOSE', 'c0e9ad51-b0a1-4a9f-bec4-4a0c2dd1b7d6');