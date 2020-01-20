package com.dev.springcar.controllers;

import com.dev.springcar.entities.Rol;
import com.dev.springcar.entities.User;
import com.dev.springcar.services.RolService;
import com.dev.springcar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("usuario")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private RolService rolService;

    @GetMapping("login")
    public String login(Model m) {
        m.addAttribute("title", "Ingrese con su cuenta");
        return "login";
    }

    @PostMapping("login")
    public String login(Model m, HttpServletRequest request) {
        String username = request.getParameter("nombre");
        String password = request.getParameter("contrasenia");

        User user = service.findUser(username, password);
        if (user != null) {
            request.getSession().setAttribute("usuario", user);
            m.addAttribute("usuario", user);
            m.addAttribute("title", "Pagina Principal");
            return "home";
        }
        m.addAttribute("message", "Usuario o Contraseña incorrectos");
        return login(m);
    }

    @GetMapping("logout")
    public String logout(Model m, HttpServletRequest request) {
        request.getSession().removeAttribute("usuario");
        m.addAttribute("title", "Pagina Principal");
        return "home";
    }

    @GetMapping
    public String usuario(Model m, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = service.findUserById(id);
        if (user != null) {
            m.addAttribute("usuario", user);
            m.addAttribute("title", "Perfil de Usuario");

            return "perfil";
        }
        m.addAttribute("title", "Pagina Principal");
        return "home";
    }

    @PostMapping("guardar")
    public String guardar(Model m, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String dni, nombre, direccion, contrasenia, telefono, fecha;
        dni = request.getParameter("dni");
        nombre = request.getParameter("nombre");
        direccion = request.getParameter("direccion");
        contrasenia = request.getParameter("contrasenia");
        telefono = request.getParameter("telefono");
        fecha = request.getParameter("nacimiento");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        String message = "";

        try {
            Date nacimiento = format.parse(fecha);
            User user = new User();
            user.setId(id);
            user.setDni(dni);
            user.setName(nombre);
            user.setAddress(direccion);
            user.setPhone(telefono);
            user.setDob(nacimiento);
            user.setPassword(contrasenia);

            if (service.updateUser(user)) {
                request.getSession().removeAttribute("usuario");
                message = "Datos guardados correctamente";
            }else {
                message = "No se pudieron guardar los cambios, vuelva a intentarlo";
            }

        } catch (ParseException e) {
            message = e.getMessage();
        } finally {
            m.addAttribute("message", message);
            m.addAttribute("title", "Pagina Principal");
        }
        return "home";
    }

    @GetMapping("signup")
    public String signup(Model m) {
        m.addAttribute("title", "Crear cuenta");
        return "signup";
    }

    @PostMapping("crear")
    public String crear(Model m, HttpServletRequest request) {
        String dni, nombre, nacimiento, direccion, telefono, contrasenia;

        dni = request.getParameter("dni");
        nombre = request.getParameter("nombre");
        nacimiento = request.getParameter("nacimiento");
        direccion = request.getParameter("direccion");
        telefono = request.getParameter("telefono");
        contrasenia = request.getParameter("contrasenia");
        String message = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date fecha = format.parse(nacimiento);
            User nuevo = new User();
            Rol rol = rolService.getRol(2);
            if (rol != null) {
                nuevo.setDni(dni);
                nuevo.setName(nombre);
                nuevo.setPhone(telefono);
                nuevo.setAddress(direccion);
                nuevo.setDob(fecha);
                nuevo.setPassword(contrasenia);
                nuevo.setRol(rol);

                if (service.addUser(nuevo)) {
                    message = "Cuenta creada exitosamente";
                }else {
                    message = "Hubo un error al intentar crear la cuenta, intente de nuevo";
                }
            }
        } catch (ParseException e) {
            message = e.getMessage();
        } finally {
            m.addAttribute("message", message);
        }

        return login(m);
    }
}
