select * from store; -- 64
select count(1) from store;

select * from vehicle; -- 267
select count(1) from vehicle;

select s.id
from store s
         left join vehicle v on v.store_id = s.id
where 1=1
-- 53
  and v.id is null ;


select count(1), v.store_id
from store s
         inner join vehicle v on v.store_id = s.id
group by v.store_id
order by 1 desc;
-- d1040a3f-af3d-4435-8973-f21df659f84f


insert into vehicle(engine, horsepower, kms, month, year, id, store_id, brand, model)
select  v1.engine, v1.horsepower, v1.kms, v1.month, v1.year, gen_random_uuid(), s1.id, v1.brand, v1.model
from vehicle v1
         full join (select s.id from store s left join vehicle v on v.store_id = s.id where v.id is null) s1 on 1=1
where v1.store_id = 'd1040a3f-af3d-4435-8973-f21df659f84f';

commit
