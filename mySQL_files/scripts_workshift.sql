select * from employee;
select * from repos;
select count(dayOff1) from repos where emp_id = 'E04';
select * from offwork;
select * from sickLeave;
select * from workshifts;
select * from program order by the_day, ws, emp_id;
select * from checkedMonths_andList;

DROP PROCEDURE IF EXISTS loadEmp;
DELIMITER $$

CREATE PROCEDURE loadEmp(IN emp_id_param varchar(3))
BEGIN 
	SELECT emp_name, emp_surname FROM employee WHERE emp_id = emp_id_param;

END $$
DELIMITER ;

select p.the_day, p.ws, p.emp_id, e.emp_name, e.emp_surname from program p join employee e WHERE  p.emp_id = e.emp_id AND  p.the_day >= '2022-01-01' AND p.the_day <= '2022-01-24' 
	ORDER BY the_day, ws, emp_id;

delete from program p1 where exists (select dwe from  
                                  (select the_day, ws, emp_id as dwe from program p2  
                                  where p1.the_day = p2.the_day AND p1.emp_id = p2.emp_id AND p1.ws = 'sick leave|time off' AND p2.ws = 'repo')
                                  as p);


SELECT emp_id, dayOff1, dayOff2 FROM repos WHERE emp_id = 'E02' AND  dayOff2 >= '2022-09-24' LIMIT 2;

select emp_id, dayOff1, dayOff2 from repos where emp_id = 'E04' AND dayOff2>'2022-07-24' limit 5;

DROP PROCEDURE IF EXISTS loadDaysOff;
DELIMITER $$

CREATE PROCEDURE loadDaysOff(IN id_param varchar(3), IN date_param date)
BEGIN 
	SELECT emp_id, dayOff1, dayOff2 from repos  WHERE emp_id = id_param AND dayOff2 > date_param limit 1;   
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS loadAllDaysOff;
DELIMITER $$

CREATE PROCEDURE loadAllDaysOff(IN id_param varchar(3))
BEGIN 
	SELECT emp_id, dayOff1, dayOff2 from repos  WHERE emp_id = id_param;   
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS loadAllDaysOffAllEmp;
DELIMITER $$

CREATE PROCEDURE loadAllDaysOffAllEmp(IN date_param1 date, IN date_param2 date)
BEGIN 
	SELECT emp_id, dayOff1, dayOff2 from repos WHERE dayOff1>=date_param1 AND dayOff2<=date_param2;   
END $$
DELIMITER ;


 CALL loadAllDaysOffAllEmp('2022-08-01', '2022-08-31');

DROP PROCEDURE IF EXISTS loadTimeOff;
DELIMITER $$

CREATE PROCEDURE loadTimeOff(IN id_param varchar(3), IN date_param date)
BEGIN 
	SELECT emp_id, timeOff from offwork  WHERE emp_id = id_param AND timeOff >= date_param;   
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS loadAllTimeOff;
DELIMITER $$

CREATE PROCEDURE loadAllTimeOff(IN id_param varchar(3))
BEGIN 
	SELECT emp_id, timeOff from offwork  WHERE emp_id = id_param;   
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS loadSickLeave;
DELIMITER $$

CREATE PROCEDURE loadSickLeave(IN id_param varchar(3), IN date_param date)
BEGIN 
	SELECT emp_id, sick_leave from sickLeave  WHERE emp_id = id_param AND sick_leave >= date_param;   
END $$
DELIMITER ;

call loadSickLeave('E01', '2022-08-31');

DROP PROCEDURE IF EXISTS loadAllSickLeave;
DELIMITER $$


CREATE PROCEDURE loadAllSickLeave(IN id_param varchar(3))
BEGIN 
	SELECT emp_id, sick_leave from sickLeave  WHERE emp_id = id_param;   
END $$
DELIMITER ;

CALL loadAllSickLeave('E01');


DROP PROCEDURE IF EXISTS loadTableView;
DELIMITER $$

CREATE PROCEDURE loadTableView()
BEGIN 
	SELECT  from_hour, to_hour, noOfEmp 
         FROM workshifts
         ORDER BY from_hour; 
 

END $$
DELIMITER ;


CALL loadTableView();



DROP PROCEDURE IF EXISTS loadWorkShifts;
DELIMITER $$

CREATE PROCEDURE loadWorkShifts()
BEGIN 
	SELECT  from_hour, to_hour, noOfEmp 
         FROM workshifts
         ORDER BY from_hour; 
 

END $$
DELIMITER ;

CALL loadWorkShifts();

DROP PROCEDURE IF EXISTS loadEmployees;
DELIMITER $$

CREATE PROCEDURE loadEmployees()
BEGIN 
	SELECT  * 
         FROM employee 
         WHERE deactivate = false
         ORDER BY emp_id; 
 

END $$
DELIMITER ;

CALL loadEmployees();


DROP PROCEDURE IF EXISTS loadChecked;
DELIMITER $$

CREATE PROCEDURE loadChecked()
BEGIN 
	SELECT  * 
         FROM checkedMonths_andList;
 
END $$
DELIMITER ;

CALL loadChecked();

DROP PROCEDURE IF EXISTS loadRandT;
DELIMITER $$

CREATE PROCEDURE loadRandT(IN date_param_start date, IN date_param_end date)
BEGIN 
	SELECT the_day, ws, emp_id from program  WHERE ws = 'repo' OR ws = 'time off' OR ws = 'sick leave' AND the_day>= date_param_start AND the_day<= date_param_end;   
END $$
DELIMITER ;

call loadRandT('2022-11-01', '2022-11-30');

DELETE FROM program WHERE the_day >= '2022-02-01' AND the_day <= '2022-02-28' AND ws != 'choose workshift' AND ws!= 'time off';
