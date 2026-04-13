-- Adminer 5.4.1 PostgreSQL 15.15 dump

CREATE TABLE "public"."attendance" (
                                       "id" uuid NOT NULL,
                                       "attendance_at" timestamptz(6),
                                       "booking_id" uuid,
                                       CONSTRAINT "attendance_pkey" PRIMARY KEY ("id")
)
    WITH (oids = false);

CREATE UNIQUE INDEX ukhfksr43f422omtl8aa9a957tk ON public.attendance USING btree (booking_id);


CREATE TABLE "public"."booking" (
                                    "id" uuid NOT NULL,
                                    "created_at" timestamptz(6),
                                    "status" character varying(255) NOT NULL,
                                    "class_schedule_id" uuid,
                                    "user_id" uuid,
                                    CONSTRAINT "booking_pkey" PRIMARY KEY ("id"),
                                    CONSTRAINT "booking_status_check" CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'CONFIRMED'::character varying, 'CANCELLED'::character varying])::text[])))
)
WITH (oids = false);


CREATE TABLE "public"."class_schedules" (
                                            "id" uuid NOT NULL,
                                            "created_at" timestamptz(6),
                                            "end_time" timestamptz(6) NOT NULL,
                                            "modified_at" timestamptz(6),
                                            "start_time" timestamptz(6) NOT NULL,
                                            "fitness_classes_id" uuid NOT NULL,
                                            "instructor_id" uuid NOT NULL,
                                            "recurring_template_id" uuid,
                                            CONSTRAINT "class_schedules_pkey" PRIMARY KEY ("id")
)
    WITH (oids = false);


CREATE TABLE "public"."fitness_class" (
                                          "id" uuid NOT NULL,
                                          "capacity" integer NOT NULL,
                                          "category" character varying(50) NOT NULL,
                                          "created_at" timestamptz(6),
                                          "description" character varying(255),
                                          "duration_minutes" integer NOT NULL,
                                          "name" character varying(100) NOT NULL,
                                          CONSTRAINT "fitness_class_pkey" PRIMARY KEY ("id")
)
    WITH (oids = false);


CREATE TABLE "public"."instructors" (
                                        "id" uuid NOT NULL,
                                        "bio" character varying(255) NOT NULL,
                                        "created_at" timestamptz(6),
                                        "modified_at" timestamptz(6),
                                        "specialization" character varying(255),
                                        "user_id" uuid NOT NULL,
                                        CONSTRAINT "instructors_pkey" PRIMARY KEY ("id")
)
    WITH (oids = false);

CREATE UNIQUE INDEX uk9bnko4773vvkd5f2pdbxvsly3 ON public.instructors USING btree (user_id);


CREATE TABLE "public"."recurring_schedule_template" (
                                                        "id" uuid NOT NULL,
                                                        "end_date_time" timestamptz(6) NOT NULL,
                                                        "rrule" character varying(255) NOT NULL,
                                                        "start_date_time" timestamptz(6) NOT NULL,
                                                        CONSTRAINT "recurring_schedule_template_pkey" PRIMARY KEY ("id")
)
    WITH (oids = false);


CREATE TABLE "public"."roles" (
                                  "id" uuid NOT NULL,
                                  "name" character varying(255) NOT NULL,
                                  CONSTRAINT "roles_pkey" PRIMARY KEY ("id"),
                                  CONSTRAINT "roles_name_check" CHECK (((name)::text = ANY ((ARRAY['ADMIN'::character varying, 'INSTRUCTOR'::character varying, 'CLIENT'::character varying])::text[])))
)
WITH (oids = false);


CREATE TABLE "public"."users" (
                                  "id" uuid NOT NULL,
                                  "created_at" timestamptz(6),
                                  "email" character varying(255) NOT NULL,
                                  "modified_at" timestamptz(6),
                                  "name" character varying(255) NOT NULL,
                                  "password" character varying(255) NOT NULL,
                                  "role_id" uuid,
                                  CONSTRAINT "users_pkey" PRIMARY KEY ("id")
)
    WITH (oids = false);

CREATE INDEX idx_user_role ON public.users USING btree (role_id);

CREATE INDEX idx_user_email ON public.users USING btree (email);

CREATE UNIQUE INDEX uk6dotkott2kjsp8vw4d0m25fb7 ON public.users USING btree (email);


ALTER TABLE ONLY "public"."attendance" ADD CONSTRAINT "fkmyobp8qdqecw7voxp72qjeqo7" FOREIGN KEY (booking_id) REFERENCES booking(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."booking" ADD CONSTRAINT "fk7udbel7q86k041591kj6lfmvw" FOREIGN KEY (user_id) REFERENCES users(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."booking" ADD CONSTRAINT "fkliirk90ij70h72le0dqfk4i66" FOREIGN KEY (class_schedule_id) REFERENCES class_schedules(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."class_schedules" ADD CONSTRAINT "fk4csrix78kbmb4790ows2igeyq" FOREIGN KEY (recurring_template_id) REFERENCES recurring_schedule_template(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."class_schedules" ADD CONSTRAINT "fk7qr1cnpuyung7fu42tsf1ma6m" FOREIGN KEY (fitness_classes_id) REFERENCES fitness_class(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."class_schedules" ADD CONSTRAINT "fkaav7b30nwra2hycpgpue5m55" FOREIGN KEY (instructor_id) REFERENCES instructors(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."instructors" ADD CONSTRAINT "fkds2m3jgxj98sd5mr1qw23ecjp" FOREIGN KEY (user_id) REFERENCES users(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."users" ADD CONSTRAINT "fkp56c1712k691lhsyewcssf40f" FOREIGN KEY (role_id) REFERENCES roles(id) NOT DEFERRABLE;

-- 2026-02-17 16:15:57 UTC