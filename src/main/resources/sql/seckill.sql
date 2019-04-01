delimiter $$
-- ROW_COUNT();返回上一条修改类型sql(delete,insert,UPDATE)影响的行数
-- ROW_COUNT():0   为修改数据  1 就是修改的行数
create
    procedure `seckill`.`execute_seckill`(
    in v_seckill_id bigint,in v_phone bigint,
    in v_kill_time timestamp ,out r_result int)
    begin
			DECLARE insert_count int DEFAULT 0;
			START TRANSACTION;
			INSERT into success_killed (seckill_id,user_phone,creat_time)
			VALUES(v_seckill_id,v_phone,v_kill_time)；
			SELECT ROW_COUNT() INTO insert_count;
			if(insert_count<0) THEN
				ROLLBACK;
				set r_result = -1;
				ELSEIF
						ROLLBACK;
				SET r_result =-2;
				ELSE
						UPDATE seckill
						set number = number -1
						WHERE seckill_id = v_seckill_id
						AND end_time >v_kill_time
						AND start_time<v_kill_time
						AND number>0;
				SELECT ROW_COUNT() INTO insert_count;
						IF (insert_count=0) THEN
							ROLLBACK;
							set r_result = 0;
						elseif (insert_count<0) THEN
							ROLLBACK;
							set r_result = -2;
						ELSE 
								COMMIT;
								set r_result = 1;
						end IF;
					
				END IF;
    END$$

delimiter ;
set @r_result=-3;
call execute_seckill(3,13838250651,now(),@r_result);
select @r_result;