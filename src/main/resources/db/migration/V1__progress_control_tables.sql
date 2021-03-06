DROP TABLE IF EXISTS progress_steps;
DROP TABLE IF EXISTS progress_control;
CREATE TABLE progress_control(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    girard varchar(32),
    progress varchar(32),
    version int(8),
    curNode int(8)
)DEFAULT CHARSET utf8;
insert into progress_control values(1,"file","20%",0,1);
CREATE TABLE progress_steps (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  girardId INT (8),
  stepNo VARCHAR (8),
  stepName VARCHAR (32),
  allFiles INT (8),
  completedFiles INT (8),
  FOREIGN KEY (girardId) REFERENCES progress_control (id)
) DEFAULT CHARSET utf8;
insert into progress_steps values(1,1,"1","fileSteps",10,4);