package view.utils;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *Classe responsável por tocar um som quando chamada
 * @author juliano
 */
public class Som {
    
    public static void tocarSom(String caminhoDoArquivo) {
        try {
            // Abre o arquivo de áudio
            File arquivoSom = new File(caminhoDoArquivo);     //"souds/pacienteAdicionadoNaSala"
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivoSom);

            // Configura o Clip de áudio
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Inicia a reprodução

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
