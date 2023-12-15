CREATE TABLE IF NOT EXISTS `user` (
                         `id` INT PRIMARY KEY AUTO_INCREMENT,
                         `username` VARCHAR(40),
                         `admin` BOOLEAN NOT NULL DEFAULT false,
                         `email` VARCHAR(255),
                         `password` VARCHAR(255),
                         `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `theme` (
                         `id` INT PRIMARY KEY AUTO_INCREMENT,
                         `name` VARCHAR(255),
                         `description` VARCHAR(1000)
);


CREATE TABLE IF NOT EXISTS `user_theme` (
                              `user_id` INT,
                              `theme_id` INT,
                              PRIMARY KEY (`user_id`, `theme_id`),
                              FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
                              FOREIGN KEY (`theme_id`) REFERENCES `theme`(`id`)
);

CREATE TABLE IF NOT EXISTS `article` (
                           `id` INT PRIMARY KEY AUTO_INCREMENT,
                           `title` VARCHAR(255),
                           `content` VARCHAR(10000),
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `theme_id` INT,
                           `user_id` INT,
                           FOREIGN KEY (`theme_id`) REFERENCES `theme`(`id`),
                           FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

INSERT INTO theme (name, description)
SELECT * FROM (SELECT 'Java', 'Java est un langage de programmation de haut niveau orienté objet créé par James Gosling et Patrick Naughton, employés de Sun Microsystems, avec le soutien de Bill Joy (cofondateur de Sun Microsystems en 1982), présenté officiellement le 23 mai 1995 au SunWorld.') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM theme WHERE name = 'Java'
) LIMIT 1;

INSERT INTO theme (name, description)
SELECT * FROM (SELECT 'React JS', 'Grâce à React, il est facile de créer des interfaces utilisateurs interactives. Définissez des vues simples pour chaque état de votre application, et lorsque vos données changeront, React mettra à jour, de façon optimale, juste les composants qui en auront besoin.') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM theme WHERE name = 'React JS'
) LIMIT 1;

INSERT INTO theme (name, description)
SELECT * FROM (SELECT 'Angular JS', 'AngularJS est fondé sur l''idée que la programmation déclarative doit être utilisée pour construire les interfaces utilisateurs et les composants logiciels de câblage, tandis que la programmation impérative excelle pour exprimer la logique métier3. La conception de AngularJS est guidée par plusieurs objectifs') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM theme WHERE name = 'Angular JS'
) LIMIT 1;

INSERT INTO theme (name, description)
SELECT * FROM (SELECT 'Node JS', 'Node.js est une plateforme logicielle libre en JavaScript, orientée vers les applications réseau évènementielles hautement concurrentes qui doivent pouvoir monter en charge. ') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM theme WHERE name = 'Node JS'
) LIMIT 1;