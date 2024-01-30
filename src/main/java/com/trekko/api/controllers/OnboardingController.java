package com.trekko.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.OnboardingTextDto;

@RestController
@RequestMapping("/onboarding")
public class OnboardingController {

    @GetMapping("/texts/about")
    public ResponseEntity<?> getAboutText() {
        return ResponseEntity.ok()
                .body(new OnboardingTextDto("Wir sind das Institut für Verkehrswesen am KIT in der Fakultät für Bauingenieur-, Geo- und Umweltwissenschaften. Unser interdisziplinäres Konzept zielt darauf ab, den Verkehr effizient und nachhaltig zu gestalten. Wir prognostizieren kurz-, mittel- und langfristig die Nutzung verschiedener Verkehrsmittel für Personen, Güter und Nachrichten."));
    }

    @GetMapping("/texts/goal")
    public ResponseEntity<?> getGoalText() {
        return ResponseEntity.ok()
                .body(new OnboardingTextDto(
                        "Ihre Unterstützung beim Sammeln und Auswerten von Wegedaten ermöglicht es uns, die Forschung in unserem Fachgebiet voranzutreiben. Durch Ihre Beiträge verfolgen wir die neuesten Trends im Verkehrswesen und dokumentieren sie, um zukünftige Verkehrsplanungen effizienter zu gestalten. Ihre Daten können die Städte von morgen maßgeblich beeinflussen. Bitte beachten Sie, dass Ihre Daten stets Ihnen gehören und wir uns überjede freiwillige Spende für unsere Forschung freuen."));
    }
}
