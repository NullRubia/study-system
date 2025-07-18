create database posts;
use posts;

CREATE TABLE users (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    userid       VARCHAR(50) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    name         VARCHAR(50) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE
);
select * from users;

CREATE TABLE studies (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  title      VARCHAR(200) NOT NULL,
  content    TEXT NOT NULL,
  max_member INT NOT NULL,
  deadline   DATE NOT NULL,
  writer_id  INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (writer_id) REFERENCES users(id) ON DELETE CASCADE
);
select * from studies;

CREATE TABLE study_applications (
  id           INT AUTO_INCREMENT PRIMARY KEY,
  user_id      INT NOT NULL,
  study_id     INT NOT NULL,
  applied_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY unique_apply (user_id, study_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (study_id) REFERENCES studies(id) ON DELETE CASCADE
);
select * from study_applications;