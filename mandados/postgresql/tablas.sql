set datestyle to sql,dmy;

DROP TABLE CLIENTE, TIENDA, REPARTIDOR, PRODUCTO, PEDIDO_PRODUCTO, PEDIDO CASCADE;

CREATE TABLE CLIENTE(
	IDCLIENTE SERIAL PRIMARY KEY,
	NOMBRE VARCHAR(30) NOT NULL,
	APATERNO VARCHAR(20) NOT NULL,
	APMATERNO VARCHAR(20) NOT NULL,
	DIRECCION VARCHAR(100) NOT NULL,
	TELEFONO VARCHAR(15) NOT NULL,
	CORREO VARCHAR(40) NOT NULL,
	CONTRASENA VARCHAR(20) NOT NULL,
	UNIQUE(CORREO)
);

CREATE TABLE REPARTIDOR(
	IDREPARTIDOR SERIAL PRIMARY KEY,
	NOMBRE VARCHAR(30) NOT NULL,
	APATERNO VARCHAR(20) NOT NULL,
	APMATERNO VARCHAR(20) NOT NULL,
	DIRECCION VARCHAR(100) NOT NULL,
	TELEFONO VARCHAR(15) NOT NULL,
	CORREO VARCHAR(40) NOT NULL,
	CONTRASENA VARCHAR(20) NOT NULL,
	UNIQUE(CORREO)
);

CREATE TABLE TIENDA(
	IDTIENDA SERIAL PRIMARY KEY,
	NOMBRE VARCHAR(30) NOT NULL,
	GIRO VARCHAR(20) NOT NULL,
	DIRECCION VARCHAR(100) NOT NULL,
	LATITUD DECIMAL(10,8),
	LONGITUD DECIMAL(10,8),
	CORREO VARCHAR(40) NOT NULL,
	CONTRASENA VARCHAR(20) NOT NULL,
	ENCARGADO VARCHAR(40) NOT NULL,
	UNIQUE(CORREO)
);

CREATE TABLE PRODUCTO(
	IDPRODUCTO SERIAL PRIMARY KEY,
	IDTIENDA INT REFERENCES TIENDA(IDTIENDA),
	NOMBRE VARCHAR(40) NOT NULL,
	DESCRIPCION TEXT NOT NULL,
	PRECIO NUMERIC NOT NULL,
	STOCK INT NOT NULL
);


CREATE TABLE PEDIDO(
	IDPEDIDO SERIAL PRIMARY KEY,
	IDCLIENTE INT REFERENCES CLIENTE(IDCLIENTE),
	IDREPARTIDOR INT REFERENCES REPARTIDOR(IDREPARTIDOR),
	IDPRODUCTO INT REFERENCES PRODUCTO(IDPRODUCTO),
	LATITUD DECIMAL(10,8),
	LONGITUD DECIMAL(10,8),
	DESCRIPCION TEXT,
	FECHA DATE NOT NULL,
	ENTREGADO BOOLEAN,
	PAGADO BOOLEAN
);



/*CLIENTES*/
INSERT INTO public.cliente(nombre, apaterno, apmaterno, direccion, telefono, correo, contrasena) VALUES ('Saul', 'Aragon', 'Moreyra', 'Yalalag #20 Col. Guelaguetza', '9511024242','drone_cam@outlook.es', 'hola');
INSERT INTO public.cliente(nombre, apaterno, apmaterno, direccion, telefono, correo, contrasena) VALUES ('Deisy', 'Ignacio', 'Luis', 'Yalalag #20 Col. Guelaguetza', '9511024242','nissy_14@hotmail.com', 'hola');

/*TIENDAS*/
INSERT INTO public.tienda(nombre, giro, direccion, latitud, longitud, correo, contrasena, encargado) VALUES ('Abarrotes Lolita', 'Abarrotes', 'Pochutla 5 Col. Guelaguetza',17.097303, -96.762724, 'lolita@hotmail.com','lolita','Lola Perez');
INSERT INTO public.tienda(nombre, giro, direccion, latitud, longitud, correo, contrasena, encargado) VALUES ('Abarrotes Pepe', 'Abarrotes', 'Pinotepa 5 Col. Guelaguetza',17.097301, -96.762721, 'pepe@hotmail.com','pepe','Pepe Perez');
INSERT INTO public.tienda(nombre, giro, direccion, latitud, longitud, correo, contrasena, encargado) VALUES ('Abarrotes Juan', 'Abarrotes', 'Etla 5 Col. Guelaguetza',17.097303, -96.762721, 'juan@hotmail.com','juan','Juan Perez');


/*PRODUCTOS*/
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (1, 'Coca-cola 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (1, 'Pepsi 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (1, 'Jugo del Valle 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (1, 'Gansito', 'Puede ser congelado o al tiempo', 12, 100);


INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (2, 'Coca-cola 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (2, 'Pepsi 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (2, 'Jugo del Valle 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (2, 'Gansito', 'Puede ser congelado o al tiempo', 12, 100);


INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (3, 'Coca-cola 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (3, 'Pepsi 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (3, 'Jugo del Valle 600 ml', 'Puede ser fria o al tiempo', 12, 100);
INSERT INTO public.producto(idtienda, nombre, descripcion, precio, stock) VALUES (3, 'Gansito', 'Puede ser congelado o al tiempo', 12, 100);

/*REPARTIDOR*/
INSERT INTO public.repartidor(nombre, apaterno, apmaterno, direccion, telefono, correo, contrasena)
    VALUES ('Josleyn', 'Moreyra', 'Reyes', 'Yalalag #20 Col. Guelaguetza', '9511024242', 'joselyn@outlook.es', 'joselyn');
