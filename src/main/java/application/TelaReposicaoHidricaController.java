package application;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import model.exceptions.ValidacaoException;
import view.utils.MascarasFX;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaReposicaoHidricaController implements Initializable {

    
    @FXML    private Button btnCalcular;
    @FXML    private CheckBox ckbDiarreia;
    @FXML    private CheckBox ckbDoencaHepatica;
    @FXML    private CheckBox ckbPerdaProteica;
    @FXML    private CheckBox ckbVomito;
    @FXML    private ComboBox<String> cmbFluidoUtilizado;
    @FXML    private ComboBox<String> cmbVolumeNaCl;
    @FXML    private ComboBox<String> cmbVolumeAlimentacaoEnteral;
    @FXML    private Label lblAaDia1;
    @FXML    private Label lblAaDia2;
    @FXML    private Label lblAaDia3;
    @FXML    private Label lblAgua10fr;
    @FXML    private Label lblAgua6fr;
    @FXML    private Label lblCremeLeite10fr;
    @FXML    private Label lblCremeLeite6fr;
    @FXML    private Label lblDextrose10fr;
    @FXML    private Label lblDextrose6fr;
    @FXML    private Label lblEnergiaAminoacidos;
    @FXML    private Label lblEnergiaGlicose;
    @FXML    private Label lblEnergiaLipideos;
    @FXML    private Label lblErroAgua;
    @FXML    private Label lblErroEspecie;
    @FXML    private Label lblErroPeso;
    @FXML    private Label lblExtratoSoja6fr;
    @FXML    private Label lblGlicoseDia1;
    @FXML    private Label lblGlicoseDia2;
    @FXML    private Label lblGlicoseDia3;
    @FXML    private Label lblGotMaxFluidoMacro15Seg;
    @FXML    private Label lblGotMaxFluidoMacroMin;
    @FXML    private Label lblGotMaxFluidoMicro15Seg;
    @FXML    private Label lblGotMaxFluidoMicroMin;
    @FXML    private Label lblGotejMaxEquipMicro;
    @FXML    private Label lblKCl10fr;
    @FXML    private Label lblKCl6fr;
    @FXML    private Label lblKContidoNoFluidoMeq;
    @FXML    private Label lblKContidoNoFluidoMl;
    @FXML    private Label lblLipideosDia1;
    @FXML    private Label lblLipideosDia2;
    @FXML    private Label lblLipideosDia3;
    @FXML    private Label lblNecessidadeDiariaEnteral;
    @FXML    private Label lblNecessidadesDiarias;
    @FXML    private Label lblNutrilon10fr;
    @FXML    private Label lblNutrilon6fr;
    @FXML    private Label lblOrnitargin6fr;
    @FXML    private Label lblPerdasGastrointestinais;
    @FXML    private Label lblPerdasOcorridas;
    @FXML    private Label lblPerdasTotais;
    @FXML    private Label lblREB;
    @FXML    private Label lblRED;
    @FXML    private Label lblRacao10fr;
    @FXML    private Label lblRequerimentoProteico;
    @FXML    private Label lblSuplemento10fr;
    @FXML    private Label lblSuplemento6fr;
    @FXML    private Label lblTempoMinAdmKHs;
    @FXML    private Label lblTempoMinimoAdmKMin;
    @FXML    private Label lblVelMaxFluido;
    @FXML    private Label lblVelSolMlMin;
    @FXML    private Label lblVelocidadeMeqH;
    @FXML    private Label lblVitaminaK;
    @FXML    private Label lblVolAminoacidos;
    @FXML    private Label lblVolumeGlicose;
    @FXML    private Label lblVolumeKcl;
    @FXML    private Label lblVolumeLipideos;
    @FXML    private Label lblVolumeSolucaoPolivitaminica;
    @FXML    private RadioButton rbCanino;
    @FXML    private RadioButton rbAguaSim;
    @FXML    private RadioButton rbFelino;
    @FXML    private RadioButton rbAguaNao;
    @FXML    private Spinner<Integer> spnDesidratacao;
    @FXML    private Spinner<Double> spnFatorDoenca;
    @FXML    private Tab repHidrica;
    @FXML    private TextField txtPeso;
    @FXML    private TextField txtKClDisponivel;
    @FXML    private TextField txtBicarbonatoDisponivel;
    
    float peso;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumero(txtPeso);
        MascarasFX.mascaraNumero(txtBicarbonatoDisponivel);
        MascarasFX.mascaraNumero(txtKClDisponivel);
        
        
        
        //Configuração da formatação dos spinners 
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15);
        SpinnerValueFactory<Double> valueFactory2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 2, 0, 0.1);
        valueFactory1.setValue(0);
        valueFactory2.setValue(1.0);
        spnDesidratacao.setValueFactory(valueFactory1);
        spnFatorDoenca.setValueFactory(valueFactory2);
        
        ObservableList<String> listaVolumesNaCl = FXCollections.observableArrayList("100 ml", "250 ml", "500 ml");
        cmbVolumeNaCl.setItems(listaVolumesNaCl);
        
        ObservableList<String> listaFluidos = FXCollections.observableArrayList("Glicofisiológica", "NaCl 0,9%", "Glicose 5%", "Ringer simples", "Ringer c/ lactato");
        cmbFluidoUtilizado.setItems(listaFluidos);
        
        ObservableList<String> listaVolumesAlimentacaoEnteral = FXCollections.observableArrayList("100 ml", "200 ml", "500 ml", "1000 ml");
        cmbVolumeAlimentacaoEnteral.setItems(listaVolumesAlimentacaoEnteral);
        
        btnCalcular.setOnAction((t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
       
                //Atributos obrigatórios
                if(!rbCanino.isSelected() && !rbFelino.isSelected()){
                    exc.adicionarErro("especie", "Selecione uma espécie!");
                }
                
                if(!rbAguaSim.isSelected() && !rbAguaNao.isSelected()){
                    exc.adicionarErro("agua", "Selecione uma opção!");
                }
                
                if (txtPeso.getText().equals("")){
                    exc.adicionarErro("peso", "Insira o peso do animal!");
                }
                
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }
                
                calcularReposicaoHidrica();
                calcularReposicaoEnergetica();
            }catch(ValidacaoException e){
                setErrorMessages(e.getErrors());
            }
            
        });
        
        //AÇÕES PARA CALCULAR AUTOMATICAMENTE:
        rbFelino.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        rbCanino.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        rbAguaSim.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        rbAguaNao.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        spnDesidratacao.setOnMouseClicked((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        spnFatorDoenca.setOnMouseClicked((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        ckbDiarreia.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        ckbDoencaHepatica.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        ckbPerdaProteica.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        ckbVomito.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        cmbFluidoUtilizado.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        cmbVolumeNaCl.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        txtPeso.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                calcularReposicaoHidrica();
                calcularReposicaoEnergetica();
            }
        });
        txtPeso.textProperty().addListener((t, ov, nv) -> {
            try{
                Float.parseFloat(txtPeso.getText());
                calcularReposicaoHidrica();
                calcularReposicaoEnergetica();
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        txtBicarbonatoDisponivel.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                calcularReposicaoHidrica();
                calcularReposicaoEnergetica();
            }
        });
        txtKClDisponivel.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                calcularReposicaoHidrica();
                calcularReposicaoEnergetica();
            }
        });
        cmbVolumeAlimentacaoEnteral.setOnAction((t) -> {
            calcularReposicaoHidrica();
            calcularReposicaoEnergetica();
        });
        
        rbAguaSim.setSelected(true);
        txtPeso.setText("10");
        rbCanino.setSelected(true);
        cmbFluidoUtilizado.getSelectionModel().select(1);
        cmbVolumeNaCl.getSelectionModel().select(0);
        cmbVolumeAlimentacaoEnteral.getSelectionModel().select(3);
        calcularReposicaoHidrica();
        calcularReposicaoEnergetica();
    }    
    
    public void calcularReposicaoHidrica(){
        
        this.peso = Float.parseFloat(txtPeso.getText());
        float kClDisponivel = Float.parseFloat(txtKClDisponivel.getText());
        float bicarbonatoDisponivel = Float.parseFloat((txtBicarbonatoDisponivel.getText()));
        float necessidadeDiariaFluido;
        float perdasOcorridas;
        float perdasGastrointestinais = 0;
        float necessidadesDiariasTotaisFluido;
        float velMaxFluido = peso * 20;
        float kContidoNoFluidoMeQ;
        float kContidoNoFluidoMl;
        float velocidadeKMeQHora;
        float necessidadeDiariaK = 1 * peso;
        float perdasOcorridasK;
        float perdasGastrointestinaisK;
        float necessidadesDiariasTotaisK;
        float volumeKCl;
        float tempoMinimoAdmKclMin;
        LocalTime tempoMinAdmKclHMin;
        float velSolucaoDiluidaMlMin;
        float gotMaxKClEquipoMicroGtMin;
        
        perdasOcorridas = 10 * spnDesidratacao.getValue() * peso;
                
        if (rbAguaNao.isSelected()){
            if (rbCanino.isSelected()){
                necessidadeDiariaFluido = 50 * peso;
            }else{
                necessidadeDiariaFluido = 70 * peso;
            }
        }else{
            necessidadeDiariaFluido = 0;
        }
        
        if (ckbVomito.isSelected() && ckbDiarreia.isSelected()){
            perdasGastrointestinais = peso * 60;
        } else if (ckbVomito.isSelected()){
            perdasGastrointestinais = peso * 40;
        }else if (ckbDiarreia.isSelected()){
            perdasGastrointestinais = peso * 50;
        }
        
        necessidadesDiariasTotaisFluido = necessidadeDiariaFluido + perdasOcorridas + perdasGastrointestinais;
        perdasOcorridasK = 2 * (perdasOcorridas / 100);
        perdasGastrointestinaisK = 3 * (perdasGastrointestinais / 100);
        necessidadesDiariasTotaisK = perdasOcorridasK + perdasGastrointestinaisK + necessidadeDiariaK;
        
        // ÍNDICES DAS SOLUÇÕES:
        // 0-Glicofisiologica
        // 1-NaCl 0,9%
        // 2-Glicose 5%
        // 3-Ringer simples
        // 4-Ringer c/ Lactato
        
        switch (cmbFluidoUtilizado.getSelectionModel().getSelectedIndex()){
            case 0:
                kContidoNoFluidoMeQ = 0;
                break;
            case 1:
                kContidoNoFluidoMeQ = 0;
                break;
            case 2:
                kContidoNoFluidoMeQ = 0;
                break;
            case 3:
                kContidoNoFluidoMeQ = necessidadesDiariasTotaisFluido / 1000 * 4;
                break;
            case 4:
                kContidoNoFluidoMeQ = necessidadesDiariasTotaisFluido / 1000 * 4;
                break;
            default:
                kContidoNoFluidoMeQ = 0;
                break;
        }
        
        kContidoNoFluidoMl = kContidoNoFluidoMeQ / kClDisponivel;
        
        if (rbCanino.isSelected()){
            velocidadeKMeQHora = peso * 0.5f;
        }else{
            velocidadeKMeQHora = peso * 0.3f;
        }
        
        volumeKCl = (necessidadesDiariasTotaisK - kContidoNoFluidoMeQ) / kClDisponivel;
        tempoMinimoAdmKclMin = (necessidadesDiariasTotaisK - kContidoNoFluidoMeQ) / velocidadeKMeQHora * 60;
        tempoMinAdmKclHMin = LocalTime.of((int) tempoMinimoAdmKclMin / 60, (int) tempoMinimoAdmKclMin % 60);

        switch (cmbVolumeNaCl.getSelectionModel().getSelectedIndex()){
            case 0:           //Fluido 100ml
                velSolucaoDiluidaMlMin = 100 / tempoMinimoAdmKclMin;
                gotMaxKClEquipoMicroGtMin = velSolucaoDiluidaMlMin * 60;
                break;
            case 1:           //Fluido 250ml
                velSolucaoDiluidaMlMin = 250 / tempoMinimoAdmKclMin;
                gotMaxKClEquipoMicroGtMin =  velSolucaoDiluidaMlMin * 60;
                break;
            case 2:           //Fluido 500ml
                velSolucaoDiluidaMlMin = 500 / tempoMinimoAdmKclMin;
                gotMaxKClEquipoMicroGtMin =  velSolucaoDiluidaMlMin * 60;
                break;
            default:
                velSolucaoDiluidaMlMin = 0;
                gotMaxKClEquipoMicroGtMin = 0;
                break;
        }

        lblNecessidadesDiarias.setText(form(necessidadeDiariaFluido));
        lblPerdasOcorridas.setText(form(perdasOcorridas));
        lblPerdasGastrointestinais.setText(form(perdasGastrointestinais));
        lblPerdasTotais.setText(form(necessidadesDiariasTotaisFluido));
        lblVelMaxFluido.setText(form(velMaxFluido));
        lblGotMaxFluidoMacroMin.setText(String.valueOf(Math.round(velMaxFluido / 60 * 20)));
        lblGotMaxFluidoMacro15Seg.setText(String.valueOf(Math.round(velMaxFluido / 60 * 20 / 4)));
        lblGotMaxFluidoMicroMin.setText(String.valueOf(Math.round(velMaxFluido)));
        lblGotMaxFluidoMicro15Seg.setText(String.valueOf(Math.round(velMaxFluido / 4)));
        lblKContidoNoFluidoMeq.setText(form(kContidoNoFluidoMeQ));
        lblKContidoNoFluidoMl.setText(form(kContidoNoFluidoMl));
        lblVelocidadeMeqH.setText(form(velocidadeKMeQHora));
        lblVolumeKcl.setText(String.valueOf(Math.round(volumeKCl)));
        lblTempoMinimoAdmKMin.setText(form(tempoMinimoAdmKclMin));
        lblTempoMinAdmKHs.setText(tempoMinAdmKclHMin.getHour()+":"+tempoMinAdmKclHMin.getMinute());
        lblVelSolMlMin.setText(form(velSolucaoDiluidaMlMin));
        lblGotejMaxEquipMicro.setText(String.valueOf(Math.round(gotMaxKClEquipoMicroGtMin)));
        }
    
    private void calcularReposicaoEnergetica(){
        int reb;
        int red;
        double reqProteico;
        double fatorDoenca = spnFatorDoenca.getValue();
        boolean doencaHepatica = ckbDoencaHepatica.isSelected();
        boolean perdaProteica = ckbPerdaProteica.isSelected();
        boolean canino = rbCanino.isSelected();
        float volumeAminoacidos;
        float energiaAminoacidos;
        float energiaGlicose;
        float volumeGlicose;
        float energiaLipideos;
        float volumeLipideos;
        float volumeSolucaoPolivitaminica;
        float vitaminaK;
        
        //Administração
        float aaDia1;
        float aaDia2;
        float aaLipideosDia1;
        float aaLipideosDia2;
        float aaGlicoseDia1;
        float aaGlicoseDia2;
        
        if (peso > 45 || peso < 2){
            reb = (int) (70 * Math.pow(peso, 0.75));
        }else{
            reb = (int) (30 * peso + 70);
        }
        
        red =  (int) (reb * spnFatorDoenca.getValue());
        
        if (canino){              //CÃES
            if (doencaHepatica){
                reqProteico = 3.5 * red / 100;
            }else{
                if (perdaProteica){
                    reqProteico = 8.5 * red / 100;
                }else{
                    if(fatorDoenca > 1.6){
                        reqProteico = 7.5 * red / 100;
                    }else{
                        if(fatorDoenca < 1.3){
                            reqProteico = 4.5 * red / 100;
                        } else{
                            reqProteico = 6 * red / 100;
                        }
                    }
                }
            }
        }else{                    //GATOS
            if (doencaHepatica){
                reqProteico = 5 * red / 100;
            }else {
                if (perdaProteica){
                    reqProteico = 9.5 * red / 100;
                }else {
                    if (fatorDoenca > 1.6){
                        reqProteico = 6 * red / 100;
                    }else{
                        if (fatorDoenca < 1.3){
                            reqProteico = 4 * red / 100;
                        }else{
                            reqProteico = 5 * red / 100;
                        }
                    }
                }
            }
        }
        
        volumeAminoacidos = (float) (reqProteico * 10);
        energiaAminoacidos = (float) (reqProteico * 4);
        energiaGlicose = (red - energiaAminoacidos) / 2;
        volumeGlicose = energiaGlicose / 1.7f;            //1,7 são as Kcal presentes por ml de glicose 50%
        energiaLipideos = energiaGlicose;                 // A energia oriunda dos lipideos deve ser igual à da glicose
        volumeLipideos = energiaLipideos / 0.9f;          //0,9 são as Kcal presentes por ml de Lipideos 10%
        volumeSolucaoPolivitaminica = 0.2f * red / 2;
        vitaminaK = peso * 0.5f;
        
                
        aaDia1 = volumeAminoacidos / 3;
        aaDia2 = 2 * aaDia1;
        aaLipideosDia1 = volumeLipideos / 3;
        aaLipideosDia2 = 2 * aaLipideosDia1;
        aaGlicoseDia1 = volumeGlicose / 3;
        aaGlicoseDia2 = 2 * aaGlicoseDia1;
        
        lblREB.setText(String.valueOf(reb));
        lblRED.setText(String.valueOf(red));
        lblRequerimentoProteico.setText(form(((float)reqProteico)));
        lblVolAminoacidos.setText(form(volumeAminoacidos));
        lblEnergiaAminoacidos.setText(form(energiaAminoacidos));
        lblEnergiaGlicose.setText(form(energiaGlicose));
        lblVolumeGlicose.setText(form(volumeGlicose));
        lblEnergiaLipideos.setText(form(energiaLipideos));
        lblVolumeLipideos.setText(form(volumeLipideos));
        lblVolumeSolucaoPolivitaminica.setText(form(volumeSolucaoPolivitaminica));
        lblVitaminaK.setText(form(vitaminaK));
        lblAaDia1.setText(form(aaDia1));
        lblAaDia2.setText(form(aaDia2));
        lblAaDia3.setText(form(volumeAminoacidos));
        lblLipideosDia1.setText(form(aaLipideosDia1));
        lblLipideosDia2.setText(form(aaLipideosDia2));
        lblLipideosDia3.setText(form(volumeLipideos));
        lblGlicoseDia1.setText(form(aaGlicoseDia1));
        lblGlicoseDia2.setText(form(aaGlicoseDia2));
        lblGlicoseDia3.setText(form(volumeGlicose));
        
        calcularAlimentacaoEnteral(red);
    }
    
    private void calcularAlimentacaoEnteral(float red){
        float volumeAlimentacaoEnteral = red / 0.96f;
        lblNecessidadeDiariaEnteral.setText(form(volumeAlimentacaoEnteral)+" ml");
        lblNutrilon6fr.setText(trocaVolumes(11f));
        lblDextrose6fr.setText(trocaVolumes(11f));
        lblExtratoSoja6fr.setText(trocaVolumes(153f));
        lblCremeLeite6fr.setText(trocaVolumes(114f));
        lblAgua6fr.setText(trocaVolumes(695f));
        lblSuplemento6fr.setText(trocaVolumes(8f));
        lblOrnitargin6fr.setText(trocaVolumes(5f));
        lblKCl6fr.setText(trocaVolumes(3f));
        lblNutrilon10fr.setText(trocaVolumes(39f));
        lblDextrose10fr.setText(trocaVolumes(16f));
        lblRacao10fr.setText(trocaVolumes(634f));
        lblCremeLeite10fr.setText(trocaVolumes(77f));
        lblAgua10fr.setText(trocaVolumes(219f));
        lblSuplemento10fr.setText(trocaVolumes(8f));
        lblKCl10fr.setText(trocaVolumes(3f));
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroAgua.setText(campos.contains("agua") ? errors.get("agua") : "");
        lblErroEspecie.setText(campos.contains("especie") ? errors.get("especie") : "");
        lblErroPeso.setText(campos.contains("peso") ? errors.get("peso") : "");
    }
    
    private String trocaVolumes(Float volumeParaLitro){
        float volumeCorrigido;
        switch (cmbVolumeAlimentacaoEnteral.getSelectionModel().getSelectedIndex()){
            case 0:
                volumeCorrigido = volumeParaLitro * 100 / 1000;
                break;
            case 1:
                volumeCorrigido = volumeParaLitro * 200 / 1000;
                break;
            case 2:
                volumeCorrigido = volumeParaLitro * 500 / 1000;
                break;
            case 3:
                volumeCorrigido = volumeParaLitro;
                break;
            default:
                volumeCorrigido = 0;
                break;
        }
        
        return form(volumeCorrigido);
    }
    
    private String form(Float numero){
        DecimalFormat df = new DecimalFormat("0.#");
        return df.format(numero);
//        .replace(".", ",")
    }
    
    
}
