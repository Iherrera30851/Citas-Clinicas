package com.citas.controller;

import com.citas.domain.Disponibilidad;
import com.citas.service.DisponibilidadService;
import com.citas.service.ProfesionalService;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/disponibilidad")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;
    private final ProfesionalService profesionalService;
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var disponibilidades = disponibilidadService.getDisponibilidades();
        model.addAttribute("disponibilidades", disponibilidades);
        model.addAttribute("totalDisponibilidades", disponibilidades.size());
        model.addAttribute("profesionales", profesionalService.getProfesionales(true));
        return "disponibilidad/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Disponibilidad disponibilidad, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";
        if (bindingResult.hasErrors()) {
            titulo = "error";
            mensaje = "disponibilidad.error04";
        } else {
            try {
                disponibilidadService.save(disponibilidad);
            } catch (IllegalArgumentException e) {
                titulo = "error";
                mensaje = "disponibilidad.error05";
            } catch (IllegalStateException e) {
                titulo = "error";
                mensaje = "disponibilidad.error06";
            }
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/disponibilidad/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idDisponibilidad, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            disponibilidadService.delete(idDisponibilidad);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "disponibilidad.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "disponibilidad.error02";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "disponibilidad.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/disponibilidad/listado";
    }
}
