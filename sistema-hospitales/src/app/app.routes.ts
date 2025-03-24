import { Routes } from '@angular/router';
import { InicioComponent } from './components/inicio/inicio.component';
import { ListaHospitalesComponent } from './components/Hospitales/lista-hospitales/lista-hospitales.component';
import { RegistroHospitalesComponent } from './components/Hospitales/registro-hospitales/registro-hospitales.component';
import { EditarHospitalesComponent } from './components/Hospitales/editar-hospitales/editar-hospitales.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CardComponent } from './components/card/card.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    {path: "", redirectTo: "card", pathMatch: "full" },
    {path:'inicio', component: InicioComponent, canActivate:[authGuard]},
    {path:'lista-hospitales', component: ListaHospitalesComponent,canActivate:[authGuard]},
    {path:'registro-hospitales', component: RegistroHospitalesComponent,canActivate:[authGuard]},
    {path:'editar-hospitales/:id', component: EditarHospitalesComponent,canActivate:[authGuard]},
    {path:'login', component: LoginComponent},
    {path:'register', component: RegisterComponent},
    {path:'card', component:CardComponent}
];
