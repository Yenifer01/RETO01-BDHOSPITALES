-- PROCEDIMIENTO ALMACENADO PARA  EL REGISTRO DE UN HOSPITAL 
create or replace NONEDITIONABLE PROCEDURE SP_HOSPITAL_REGISTRAR(
    p_idDistrito IN NUMBER,
    p_Nombre IN VARCHAR2,
    p_Antiguedad IN NUMBER,
    p_Area IN NUMBER,
    p_idSede IN NUMBER,
    p_idGerente IN NUMBER,
    p_idCondicion IN NUMBER
) IS
    v_existe NUMBER;
    v_count NUMBER;
BEGIN
    IF p_Nombre IS NULL OR LENGTH(TRIM(p_Nombre)) = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Error: El nombre del hospital no puede estar vacío.');
    END IF;
    SELECT COUNT(*) INTO v_existe FROM HOSPITAL WHERE nombre = p_Nombre;
    IF v_existe > 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Error: Ya existe un hospital con el nombre "' || p_Nombre || '".');
    END IF;

    IF p_Antiguedad <= 0 OR p_Area <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Error: La antigüedad y el área deben ser mayores que 0.');
    END IF;

    SELECT COUNT(*) INTO v_count FROM DISTRITO WHERE idDistrito = p_idDistrito;
    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20004, 'Error: El distrito especificado no existe.');
    END IF;

    SELECT COUNT(*) INTO v_count FROM SEDE WHERE idSede = p_idSede;
    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20005, 'Error: La sede especificada no existe.');
    END IF;

    SELECT COUNT(*) INTO v_count FROM GERENTE WHERE idGerente = p_idGerente;
    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20006, 'Error: El gerente especificado no existe.');
    END IF;

    SELECT COUNT(*) INTO v_count FROM CONDICION WHERE idCondicion = p_idCondicion;
    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20007, 'Error: La condición especificada no existe.');
    END IF;

    INSERT INTO HOSPITAL (idDistrito, nombre, antiguedad, area, idSede, idGerente, idCondicion, fechaRegistro)
    VALUES (p_idDistrito, p_Nombre, p_Antiguedad, p_Area, p_idSede, p_idGerente, p_idCondicion, SYSDATE);

    DBMS_OUTPUT.PUT_LINE('Hospital registrado correctamente.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al registrar el hospital: ' || SQLERRM);
        RAISE;
END SP_HOSPITAL_REGISTRAR;

-- EJECUCIÓN DEL PROCEDIMIENTO
BEGIN 
SP_HOSPITAL_REGISTRAR(4, 'Hospital Belen', 10, 967.79, 2,4,3);
END;
BEGIN
   SP_HOSPITAL_REGISTRAR(3,'Hospital Regional del Cusco',8,997.75,2,3,2);
END;


-- PROCEDIMIENTO ALMACENADO PARA  ACTUALIZAR UN HOSPITAL 
create or replace NONEDITIONABLE PROCEDURE SP_HOSPITAL_ACTUALIZAR(
    p_idHospital IN NUMBER,
    p_idDistrito IN NUMBER,
    p_Nombre IN VARCHAR2,
    p_Antiguedad IN NUMBER,
    p_Area IN NUMBER,
    p_idSede IN NUMBER,
    p_idGerente IN NUMBER,
    p_idCondicion IN NUMBER
) IS
    v_count NUMBER;
    v_nombre_existente NUMBER;
    v_existente HOSPITAL%ROWTYPE; 
    ex_no_cambio EXCEPTION; 
BEGIN
    SELECT COUNT(*) 
    INTO v_count
    FROM HOSPITAL
    WHERE idHospital = p_idHospital;
    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'No se encontró el hospital con ID: ' || p_idHospital);
    END IF;

    IF p_Antiguedad <= 0 OR p_Area <= 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Los valores de antigüedad y área deben ser mayores a 0.');
    END IF;

    SELECT COUNT(*) 
    INTO v_nombre_existente
    FROM HOSPITAL
    WHERE Nombre = p_Nombre AND idHospital <> p_idHospital;

    IF v_nombre_existente > 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Ya existe un hospital con el nombre "' || p_Nombre || '".');
    END IF;

    SELECT * INTO v_existente FROM HOSPITAL WHERE idHospital = p_idHospital;
    IF v_existente.idDistrito = p_idDistrito 
       AND v_existente.Nombre = p_Nombre 
       AND v_existente.Antiguedad = p_Antiguedad
       AND v_existente.Area = p_Area 
       AND v_existente.idSede = p_idSede 
       AND v_existente.idGerente = p_idGerente 
       AND v_existente.idCondicion = p_idCondicion THEN
        RAISE ex_no_cambio;  
    END IF;
    UPDATE HOSPITAL
    SET idDistrito = p_idDistrito, 
        Nombre = p_Nombre, 
        Antiguedad = p_Antiguedad,
        Area = p_Area, 
        idSede = p_idSede, 
        idGerente = p_idGerente, 
        idCondicion = p_idCondicion,
        fechaModificacion = SYSDATE 
    WHERE idHospital = p_idHospital;
    DBMS_OUTPUT.PUT_LINE('Hospital actualizado correctamente.');
EXCEPTION
    WHEN ex_no_cambio THEN
    RAISE_APPLICATION_ERROR(-20004, 'No se ha realizado ningún cambio en los datos.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al actualizar el hospital: ' || SQLERRM);
END SP_HOSPITAL_ACTUALIZAR;

-- EJECUCIÓN DEL PROCEDIMIENTO
SET SERVEROUTPUT ON
BEGIN
    SP_HOSPITAL_ACTUALIZAR(1,1, 'Hospital Nacional Arzobispo Loayza', 10, 987.78, 1,1,2);
END;
BEGIN
    SP_HOSPITAL_ACTUALIZAR(1,1, 'Hospital Goyeneche', 11, 987.78, 1,1,2);
END;


-- PROCEDIMIENTO ALMACENADO PARA ELIMINAR UN  HOSPITAL 
create or replace NONEDITIONABLE PROCEDURE SP_HOSPITAL_ELIMINAR(
    p_idHospital IN NUMBER
) IS
    v_count NUMBER;
BEGIN
   
    SELECT COUNT(*) 
    INTO v_count 
    FROM HOSPITAL
    WHERE idHospital = p_idHospital;

    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'No se encontró el hospital con ID ' || p_idHospital || '.');
    END IF;

    DELETE FROM HOSPITAL WHERE idHospital = p_idHospital;
    COMMIT;
   
    DBMS_OUTPUT.PUT_LINE('El Hospital con ID ' || p_idHospital || ' fue eliminado correctamente.');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20002, 'Error al eliminar el hospital: ' || SQLERRM);
END SP_HOSPITAL_ELIMINAR;

-- EJECUCIÓN DEL PROCEDIMIENTO
BEGIN
    SP_HOSPITAL_ELIMINAR(12);
END;
SELECT * FROM HOSPITAL;

-- PROCEDIMIENTO ALMACENADO PARA LISTAR HOSPITALES
create or replace NONEDITIONABLE PROCEDURE SP_HOSPITAL_LISTAR (
    p_NomHospital IN HOSPITAL.Nombre%TYPE DEFAULT NULL,
    p_descDistrito IN DISTRITO.descDistrito%TYPE DEFAULT NULL,
    p_descCondicion IN CONDICION.descCondicion%TYPE DEFAULT NULL,
    p_gerente IN GERENTE.descGerente%TYPE DEFAULT NULL,
    p_sede IN SEDE.descSede%TYPE DEFAULT NULL,
    p_result OUT SYS_REFCURSOR 
) IS
BEGIN
    OPEN p_result FOR 
        SELECT h.idHospital, d.descDistrito, h.Nombre, h.Antiguedad, h.Area, 
               s.descSede, g.descGerente, c.descCondicion, h.fechaRegistro
        FROM HOSPITAL h
        INNER JOIN SEDE s ON h.idSede = s.idSede
        INNER JOIN DISTRITO d ON h.idDistrito = d.idDistrito
        INNER JOIN GERENTE g ON h.idGerente = g.idGerente
        INNER JOIN CONDICION c ON h.idCondicion = c.idCondicion
        WHERE (p_NomHospital IS NULL OR UPPER(h.Nombre) LIKE '%' || UPPER(p_NomHospital) || '%')
            AND (p_descDistrito  IS NULL OR UPPER(d.descDistrito) LIKE '%' || UPPER(p_descDistrito) || '%')
            AND (p_descCondicion IS NULL OR UPPER(c.descCondicion) LIKE '%' || UPPER(p_descCondicion) || '%')
            AND (p_gerente IS NULL OR UPPER(g.descGerente) LIKE '%' || UPPER(p_gerente) || '%')
            AND (p_sede IS NULL OR UPPER(s.descSede) LIKE '%' || UPPER(p_sede) || '%');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al listar los hospitales: ' || SQLERRM);
END SP_HOSPITAL_LISTAR;
-- EJECUCIÓN DEL PROCEDIMIENTO

DECLARE 
    v_result SYS_REFCURSOR;
    v_idHospital NUMBER;
    v_descDistrito VARCHAR2(100);
    v_Nombre VARCHAR2(100);
    v_Antiguedad NUMBER;
    v_Area NUMBER;
    v_descSede VARCHAR2(100);
    v_descGerente VARCHAR2(100);
    v_descCondicion VARCHAR2(100);
    v_fechaRegistro DATE;
BEGIN
    SP_HOSPITAL_LISTAR(
        p_NomHospital   => NULL, 
        p_descDistrito  => NULL, 
        p_descCondicion => NULL, 
        p_gerente       => NULL, 
        p_sede          => NULL, 
        p_result        => v_result
    );
    LOOP
        FETCH v_result INTO v_idHospital, v_descDistrito, v_Nombre, v_Antiguedad, 
                           v_Area, v_descSede, v_descGerente, v_descCondicion, v_fechaRegistro;
        EXIT WHEN v_result%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('ID: ' || v_idHospital || ', Nombre: ' || v_Nombre || 
                             ', Distrito: ' || v_descDistrito || ', Sede: ' || v_descSede ||
                             ', Gerente: ' || v_descGerente || ', Condición: ' || v_descCondicion);
    END LOOP;
    CLOSE v_result;
END;



