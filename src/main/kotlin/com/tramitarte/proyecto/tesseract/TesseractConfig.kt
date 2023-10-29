package com.tramitarte.proyecto.tesseract

import net.sourceforge.tess4j.Tesseract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
public class TesseractConfig {
    @Bean
    fun tesseract(): Tesseract {
        val tesseract = Tesseract()
        tesseract.setDatapath("src/main/resources/tessdata")
        return tesseract
    }
}