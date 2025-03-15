-- PROCEDIMIENTO ALMACENADO PARA  EL REGISTRO DE UN HOSPITAL 
CREATE OR REPLACE PROCEDURE SP_HOSPITAL_REGISTRAR(
    p_idDistrito IN NUMBER,
    p_Nombre IN VARCHAR2,
    p_Antiguedad IN NUMBER,
    p_Area IN NUMBER,
    p_idSede IN NUMBER,
    p_idGerente IN NUMBER,
    p_idCondicion IN NUMBER
) IS
    v_existe NUMBER;
BEGIN
    SELECT COUNT(*) 
    INTO v_existe 
    FROM HOSPITAL
    WHERE nombre = p_Nombre;
    IF v_existe > 0 THEN
          RAISE_APPLICATION_ERROR(-20002, 'Error: Ya existe un hospital con el nombre "' || p_Nombre || '".');
    END IF;
    IF p_Antiguedad <= 0 OR p_Area <= 0 THEN
         RAISE_APPLICATION_ERROR(-20003, 'Error: La antigüedad y el área deben ser mayores que 0.');
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
CREATE OR REPLACE PROCEDURE SP_HOSPITAL_ACTUALIZAR(
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
        DBMS_OUTPUT.PUT_LINE('No se ha realizado ningún cambio en los datos.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al actualizar el hospital: ' || SQLERRM);
END SP_HOSPITAL_ACTUALIZAR;

-- EJECUCIÓN DEL PROCEDIMIENTO
BEGIN
    SP_HOSPITAL_ACTUALIZAR(1,1, 'Hospital Nacional Arzobispo Loayza', 11, 987.78, 1,1,2);
END;
BEGIN
    SP_HOSPITAL_ACTUALIZAR(1,1, 'Hospital Goyeneche', 10, 987.78, 1,1,2);
END;

-- PROCEDIMIENTO ALMACENADO PARA ELIMINAR UN  HOSPITAL 
CREATE OR REPLACE PROCEDURE SP_HOSPITAL_ELIMINAR(
    p_idHospital IN NUMBER
) IS
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) 
    INTO v_count 
    FROM HOSPITAL
    WHERE idHospital = p_idHospital;
    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Error: No se encontró el hospital con ID ' || p_idHospital || '.');
    ELSE
        DELETE FROM HOSPITAL WHERE idHospital = p_idHospital;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('El Hospital con ID ' || p_idHospital || ' fue eliminado correctamente.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al eliminar el hospital: ' || SQLERRM);
        ROLLBACK;
END SP_HOSPITAL_ELIMINAR;

-- EJECUCIÓN DEL PROCEDIMIENTO
BEGIN
    SP_HOSPITAL_ELIMINAR(2);
END;

-- PROCEDIMIENTO ALMACENADO PARA LISTAR HOSPITALES
CREATE OR REPLACE PROCEDURE SP_HOSPITAL_LISTAR (
    p_NomHospital IN HOSPITAL.Nombre%TYPE DEFAULT NULL,
    p_descDistrito IN DISTRITO.descDistrito%TYPE DEFAULT NULL,
    p_descCondicion IN CONDICION.descCondicion%TYPE DEFAULT NULL
) IS
    CURSOR c_hospital IS 
        SELECT h.idHospital, d.descDistrito, h.Nombre, h.Antiguedad, h.Area, 
               s.descSede, g.descGerente, c.descCondicion, h.fechaRegistro
        FROM HOSPITAL h
        INNER JOIN SEDE s ON h.idSede = s.idSede
        INNER JOIN DISTRITO d ON h.idDistrito = d.idDistrito
        INNER JOIN GERENTE g ON h.idGerente = g.idGerente
        INNER JOIN CONDICION c ON h.idCondicion = c.idCondicion
        WHERE (p_NomHospital IS NULL OR UPPER(h.Nombre) LIKE '%' || UPPER(p_NomHospital) || '%')
          AND (p_descDistrito  IS NULL OR UPPER(d.descDistrito) LIKE '%' || UPPER(p_descDistrito) || '%')
          AND (p_descCondicion IS NULL OR UPPER(c.descCondicion) LIKE '%' || UPPER(p_descCondicion) || '%');
    v_count NUMBER := 0; 
BEGIN
    FOR v_hospital IN c_hospital LOOP
        v_count := v_count + 1; 
        DBMS_OUTPUT.PUT_LINE('ID: ' || v_hospital.idHospital || 
                             ' | Nombre: ' || v_hospital.Nombre || 
                             ' | Gerente: ' || v_hospital.descGerente || 
                             ' | Condición: ' || v_hospital.descCondicion || 
                             ' | Sede: ' || v_hospital.descSede || 
                             ' | Distrito: ' || v_hospital.descDistrito);
    END LOOP;
    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No se encontraron hospitales con los datos ingresados.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al listar los hospitales: ' || SQLERRM);
END SP_HOSPITAL_LISTAR;

-- EJECUCIÓN DEL PROCEDIMIENTO
-- Lince
-- Cayma
-- San Jerónimo
BEGIN
    SP_HOSPITAL_LISTAR(null, 'san jerónimo', null);
END;





