set character set utf8mb4;
create table t_classroom
(
	id bigint auto_increment
		primary key,
	name varchar(30) default '' not null comment '名称',
	enable_state int unsigned default 1 not null comment '停启用状态，1启用 2停用'
)
comment '教室';

create table t_color
(
	id bigint auto_increment comment 'id'
		primary key,
	name varchar(30) default '' not null comment '名称',
	value char(7) default '' not null comment '值'
)
comment '颜色';

create table t_course
(
	id bigint auto_increment comment 'id'
		primary key,
	name varchar(30) default '' not null comment '名称',
	enable_state int unsigned default 1 not null comment '停启用状态，1启用 2停用',
	duration int unsigned null comment '课程时长，单位分钟',
	background_color char(7) default '' not null comment '背景颜色'
)
comment '课程';

create table t_course_scheduling
(
	id bigint auto_increment comment 'id'
		primary key,
	classroom_id bigint default 0 not null comment '教室id',
	course_id bigint default 0 not null comment '课程id',
	teacher_id bigint default 0 not null comment '教师id',
	date date null comment '日期',
	attend_time time null comment '上课时间',
	finish_time time null comment '下课时间'
)
comment '排课';

create table t_school
(
	id bigint auto_increment comment 'id'
		primary key,
	名称 varchar(50) default '' not null comment '名称'
)
comment '学校';

create table t_teacher
(
	id bigint auto_increment comment 'id'
		primary key,
	name varchar(10) default '' not null comment '姓名',
	enable_state int unsigned default 1 not null comment '停启用状态，1启用 2停用'
)
comment '教师';

