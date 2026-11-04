package com.citas.controller;

import com.citas.service.CitaService;
import com.citas.service.NotaClinicaService;
import com.citas.service.ProfesionalService;
import java.time.LocalDate;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/agenda")
public class AgendaController {

    private final CitaService citaService;
    private final NotaClinicaService notaClinicaService;
    private final ProfesionalService profesionalService;
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("profesionales", profesionalService.getProfesionales(true));
        return "agenda/listado";
    }

    @GetMapping("/{idProfesional}")
    public String agenda(@PathVariable("idProfesional") Integer idProfesional,
            @RequestParam(required = false) LocalDate fecha, Model model) {
        if (fecha == null) {
            fecha = LocalDate.now();
        }
        model.addAttribute("idProfesionalActual", idProfesional);
        model.addAttribute("fecha", fecha);
        model.addAttribute("citas", citaService.getCitasDelDia(idProfesional, fecha));
        model.addAttribute("profesionales", profesionalService.getProfesionales(true));
        return "agenda/listado";
    }

    @PostMapping("/estado")
    public String estado(@RequestParam Integer idCita, @RequestParam String estado,
            @RequestParam Integer idProfesional, @RequestParam LocalDate fecha,
            RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";
        try {
            citaService.cambiarEstado(idCita, estado);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "cita.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "cita.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/agenda/" + idProfesional + "?fecha=" + fecha;
    }

    @PostMapping("/nota")
    public String nota(@RequestParam Integer idCita, @RequestParam String descripcion,
            @RequestParam Integer idProfesional, @RequestParam LocalDate fecha,
            RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "nota.guardada";
        try {
            notaClinicaService.guardar(idCita, descripcion);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "nota.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "nota.error02";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/agenda/" + idProfesional + "?fecha=" + fecha;
    }
}
