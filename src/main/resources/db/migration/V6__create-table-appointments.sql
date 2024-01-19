create table appointments (

    id bigint not null auto_increment,
    id_doctor bigint not null,
    id_patient bigint not null,
    date datetime not null,

    primary key (id),
    constraint fk_appointments_id_doctor foreign key (id_doctor) references doctors (id),
    constraint fk_appointments_id_patient foreign key (id_patient) references patients (id)
);