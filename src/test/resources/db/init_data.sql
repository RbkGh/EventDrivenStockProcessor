CREATE TABLE IF NOT EXISTS public.product_qty
(
    id bigint NOT NULL,
    total_qty integer,
    CONSTRAINT product_qty_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product_qty
    OWNER to "stock-user";

CREATE TABLE IF NOT EXISTS public.product
(
    id bigint NOT NULL,
    product_imageurl character varying(255) COLLATE pg_catalog."default",
    product_name character varying(255) COLLATE pg_catalog."default",
    product_short_code character varying(255) COLLATE pg_catalog."default",
    product_qty_id bigint,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT ukevhjmpxt8allgqp6eo7c0vgis UNIQUE (product_short_code),
    CONSTRAINT uki6vjsfskvj61cn5bsksgn3tsl UNIQUE (product_qty_id),
    CONSTRAINT fkc9wbtt8qd8nrlqllvkpxap2r9 FOREIGN KEY (product_qty_id)
        REFERENCES public.product_qty (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to "stock-user";


