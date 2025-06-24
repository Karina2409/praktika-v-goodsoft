-- Задание 1.1 Создать схему authorization
create schema if not exists "authorization";

-- Задание 1.2 Создать таблицу users
create table if not exists "authorization".users (
	id integer generated always as identity primary key,
	login text not null unique,
	password text not null,
	name text not null,
	birthday date
);

-- Задание 1.3 Создать таблицу roles
create table if not exists "authorization".roles (
	id integer generated always as identity primary key,
	name text not null unique
);

-- Изменение пути поиска схем
SET search_path TO authorization;

-- Задание 2.1 В таблицу users добавить колонку age 
-- (непустое, значения только больше 18)
alter table users 
add column age integer not null check (age > 18);

-- Задание 2.2 В таблицу users добавить колонку salary (пустое)
alter table users 
add column salary numeric(2);

-- Задание 2.3 Добавить индекс на имя пользователя
create index id_users_name on users(name);

-- Задание 2.4 Добавить связь с таблицей ролей
alter table users
add column role_id integer;

alter table users
add constraint fk_users_roles foreign key (role_id) references roles(id);

-- Задание 3.1 Заполнить таблицу roles данными
insert into roles(name) values
('Администратор'),
('Пользователь'),
('Гость'),
('Модератор'),
('Менеджер');

-- Задание 3.2 Заполнить таблицу users данными
insert into users(login, password, name, birthday, age, salary, role_id) values
('karina', '1234_Karina', 'Карина', '2004-09-24', 20, 3000, 1),
('ivan', '1234_Ivan', 'Иван', '2000-03-23', 25, 2000, 2),
('vasya123', '1234_Vasya', 'Вася', '1990-05-12', 35, 5000, 2),
('petya456', '1234_Petya', 'Петя', '1988-11-23', 36, 4000, 3),
('masha789', '1234_Masha', 'Маша', '1995-07-14', 29, NULL, 2),
('oleg321', '1234_Oleg', 'Олег', '1980-01-30', 45, 7000, 4),
('anna654', '1234_Anna', 'Анна', NULL, 25, 5500, 4),
('ivan987', '12345_Ivan', 'Иван', '1993-09-05', 31, NULL, 2),
('katya234', '1234_Katya', 'Катя', '1997-04-22', 28, 1800, 5),
('sergey345', '1234_Sergey', 'Сергей', '1985-03-17', 40, 2100, 3),
('lena876', '1234_Lena', 'Лена', NULL, 27, 5200, 2),
('dima543', '1234_Dima', 'Дима', '1991-12-11', 32, NULL, 4),
('nastya210', '1234_Nastya', 'Настя', '1996-08-29', 28, 4700, 5),
('igor432', '1234_Igor', 'Игорь', '1983-07-20', 41, 7200, 1),
('olga567', '1234_Olga', 'Ольга', '1994-10-09', 30, NULL, 2),
('max678', '1234_Max', 'Максим', NULL, 34, 1800, 3),
('tanya789', '1234_Tanya', 'Таня', '1992-02-20', 34, 3000, 4);

-- Задание 4.1 Выбрать всех пользователей
select * from users;

-- Задание 4.2 Выбрать всех пользователей с непустой датой рождения
select * from users
where birthday is not null;

-- Задание 4.3 Выбрать всех пользователей с датой рождения 
-- в заданных пределах (от и до)
select * from users
where birthday between '1993-09-05' and '2000-09-24';

-- Задание 4.4 Выбрать количество пользователей с одинаковым 
-- возрастом (на выходе 2 колонки - возраст и количество)
select age, count(*) as total
from users
group by age;

-- Задание 4.5 К предыдущему добавить ограничение по количеству 
-- только больше 1 и отсортировать по количеству по убыванию
select age, count(*) as total
from users
group by age
having count(*) > 1
order by total desc;

-- Задание 4.6 Выбрать всех пользователей с ролями (роли так же вывести)
select u.*, r.name as role_name
from users u
join roles r on u.role_id = r.id;

-- Задание 4.7 Выбрать всех пользователей и их роли
select u.*, r.name as role_name
from users u
left join roles r on u.role_id = r.id;

-- Задание 4.8 Выбрать всех пользователей и их роли (5 записей)
select u.*, r.name as role_name
from users u
left join roles r on u.role_id = r.id
limit 5;

-- Задание 4.9 Выбрать всех пользователей и их роли начиная с 3й записи
select u.*, r.name as role_name
from users u
left join roles r on u.role_id = r.id
offset 2
limit 5;

-- Задание 5.1 Изменить колонку зарплата на непустое со значением 
-- по умолчанию - 1000 (старые значения сохранить)
alter table users alter column salary set default 1000;
update users set salary=1000 where salary is null;
alter table users alter column salary set not null;

-- 5.2 Посчитать среднюю зарплату пользователей с возрастом 
-- меньше 25 и отдельно (использовать объединение запросов) больше 25
select 'меньше 25' as age, avg(salary) as average_salary
from users
where age <= 25

union

select 'больше 25', avg(salary)
from users
where age > 25;

-- 6.1 Всем у кого зарплата меньше 3000 добавить к зарплате 20%
update users
set salary = salary * 1.2
where salary < 3000;

-- 6.2 Тем у кого имя начинается на заданную букву роль установить на "Менеджер"
update users
set role_id = 5
where name ilike 'п%';

-- 7 Перевести хранение ролей на схему с возможностью задания нескольких ролей 
-- одному пользователю. Предусмотреть сохранение имеющихся у пользователей ролей. 
-- Подумать о первичном ключе.
create table users_roles (
	user_id int references users(id) on delete cascade,
	role_id int references roles(id) on delete cascade,
	primary key (user_id, role_id)
);

insert into users_roles (user_id, role_id)
select id, role_id from users where role_id is not null;

alter table users drop column role_id;

-- 8.1 Назначить заданному пользователю какие либо 3 роли которые у него отсутствуют
insert into users_roles (user_id, role_id)
select 1, r.id
from roles r
where r.id not in (
	select role_id from users_roles where user_id = 1
)
limit 3;

-- 8.2 Выбрать всех пользователей и их роли
select u.*, r.name as role_name
from users u
left join users_roles ur on u.id = ur.user_id
left join roles r on ur.role_id = r.id
order by u.id;

-- 8.3 Выбрать только пользователей с ролями
select u.*, r.name as role_name
from users u
join users_roles ur on u.id = ur.user_id
join roles r on ur.role_id = r.id
order by u.id;

-- 8.4 Удалить выбранного пользователя (у которого есть роли)
delete from users
where id = 6
and exists (
	select 1 from users_roles where user_id = 6
);

-- 9.1 Выбрать всех пользователей и все роли независимо от привязок одним запросом
select u.*, r.name as role_name
from users u
cross join roles r
order by u.id, r.id;

-- 9.2 Выбрать всех пользователей и все роли для которых соответственно нет 
-- привязок ролей и пользователей
select u.*, r.name as role_name
from users u
cross join roles r
where not exists (
	select 1 from users_roles ur
	where ur.user_id = u.id and ur.role_id = r.id
)
order by u.id, r.id;
