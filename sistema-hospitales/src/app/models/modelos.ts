export interface ListaHospital {
  idHospital: number;
  descDistrito: string;
  nombre: string;
  antiguedad: number;
  area: number;
  descSede: string;
  descGerente: string;
  descCondicion: string;
  fechaRegistro: string;  
}

export interface RegistroHospital {
  idDistrito: number;
  nombre: string;
  antiguedad: number;
  area: number;
  idSede: number;
  idGerente: number;
  idCondicion: number;
}
export interface ActualizarHospital {
  idHospital: number;
  idDistrito: number;
  nombre: string;
  antiguedad: number;
  area: number;
  idSede: number;
  idGerente: number;
  idCondicion: number;
}


export interface Distrito {
  iddistrito?: number;
  idprovincia: number;
  descdistrito: string;
  fecharegistro: Date;
}

export interface Sede {
  idsede?: number;
  descsede: string;
  fecharegistro: Date;
}

export interface Gerente {
  idgerente?: number;
  descgerente: string;
  fecharegistro: Date;
}

export interface Condicion {
  idcondicion?: number;
  desccondicion: string;
  fecharegistro: Date;
}
