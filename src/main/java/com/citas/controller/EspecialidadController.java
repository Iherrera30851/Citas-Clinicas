package com.citas.controller;

import com.citas.domain.Especialidad;
import com.citas.service.EspecialidadService;
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
@RequestMapping("/especialidad")
public class EspecialidadController {

    private final EspecialidadService especialidadService;
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var especialidades = especialidadService.getEspecialidades(false);
        model.addAttribute("especialidades", especialidades);
        model.addAttribute("totalEspecialidades", especialidades.size());
        return "especialidad/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Especialidad especialidad, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";
        if (bindingResult.hasErrors()) {
            titulo = "error";
            mensaje = "especialidad.error04";
        } else {
            try {
                especialidadService.save(especialidad);
            } catch (DataIntegrityViolationException e) {
                titulo = "error";
                mensaje = "especialidad.error05";
            }
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/especialidad/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idEspecialidad, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            especialidadService.delete(idEspecialidad);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "especialidad.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "especialidad.error02";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "especialidad.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/especialidad/listado";
    }
}
