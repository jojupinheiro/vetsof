package pdf;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.awt.Color;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.classes.prescricoes.FormatacaoPrescricao;
import model.classes.prescricoes.Prescricao;
import model.services.PrescricaoService;
import view.utils.Utils;

/**
 *
 * @author juliano
 */
public class ImpressaoPdf {
    
    private String caminhoPrescricoes;
    private Document document;
    private Prescricao prescricao;
    private List<FormatacaoPrescricao> listaFormatacoes = new PrescricaoService().getFormatacaoAtivaDaPrescricao();
    
    public ImpressaoPdf(Prescricao prescricao){
        this.document = new Document(PageSize.A4, 36, 36, 25, 20);
        this.prescricao = prescricao;
        this.caminhoPrescricoes = "src/main/resources/prescricoes/" + prescricao.getId() + ".pdf";
//        this.caminhoPrescricoes = "../prescricoes/" + prescricao.getId() + ".pdf";
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(caminhoPrescricoes));
            this.document.open();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        gerarCabecalho(prescricao);
        gerarCorpo(prescricao);
        gerarRodape(prescricao, writer);
        imprimir(prescricao);
    }
    
    public void gerarCabecalho(Prescricao prescricao){
        Image logo = null;
        try {
            FormatacaoPrescricao formLogo = retornarFormatacao(22);
            logo = Image.getInstance("src/main/java/pdf/logo" + formLogo.getModelo() + ".png");
            
            int tamanhoLogotipo = formLogo.getTamanho();
            int formatoLogotipo = formLogo.getAlinhamento();
            int alturaLogo = 50;
            int larguraLogo = 50;
            switch (tamanhoLogotipo) {
                case 1:
                    switch (formatoLogotipo) {
                        case 1:
                            larguraLogo = 60;
                            alturaLogo = 60;
                            break;
                        case 2:
                            larguraLogo = 100;
                            alturaLogo = 60;
                            break;
                        case 3:
                            larguraLogo = 60;
                            alturaLogo = 100;
                            break;
                    }
                    break;
                case 2:
                    switch (formatoLogotipo) {
                        case 1:
                            larguraLogo = 80;
                            alturaLogo = 80;
                            break;
                        case 2:
                            larguraLogo = 120;
                            alturaLogo = 80;
                            break;
                        case 3:
                            larguraLogo = 80;
                            alturaLogo = 120;
                            break;
                    }
                    break;
                case 3:
                    switch (formatoLogotipo) {
                        case 1:
                            larguraLogo = 100;
                            alturaLogo = 100;
                            break;
                        case 2:
                            larguraLogo = 140;
                            alturaLogo = 100;
                            break;
                        case 3:
                            larguraLogo = 100;
                            alturaLogo = 140;
                            break;
                    }
                    break;
            }
            logo.scaleToFit(larguraLogo, alturaLogo);
            
            float posicaoX = 5f + formLogo.getRecuo();
            float posicaoY = 790f - (alturaLogo / 10) -formLogo.getEspacamento();
            logo.setAbsolutePosition(posicaoX, posicaoY);
            
            document.add(logo);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        FormatacaoPrescricao formNomeClinica = retornarFormatacao(1);
        Paragraph p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_CENTER);
        p1.add(
                new Chunk(
                         formNomeClinica.getTexto(), 
                        new Font(Font.HELVETICA, formNomeClinica.getTamanho(), getFontStyle(formNomeClinica), 
                                Color.decode(formNomeClinica.getCor()))
                )
        );
        
        FormatacaoPrescricao formDescricaoClinica = retornarFormatacao(2);
        p1.add(
                new Chunk(
                        formDescricaoClinica.getTexto()+"\n", 
                        new Font(Font.HELVETICA, formDescricaoClinica.getTamanho(),getFontStyle(formNomeClinica), 
                                Color.decode(formDescricaoClinica.getCor()))
                )
        );
        
        
//        p1.add(
//                new Chunk(
//                        "Rua Osvaldo Aranha, 455, Venâncio Aires - RS\n", 
//                        new Font(Font.HELVETICA, 9)
//                )
//        );
//        
//        p1.add(
//                new Chunk(
//                        "Fones: (51) 3741 7921 e (51) 99549 0865", 
//                        new Font(Font.HELVETICA, 9)
//                )
//        );
        
        this.document.add(p1);
//        this.documento.add(new Paragraph(" "));  //Pular linha
        this.document.add(new Paragraph(" "));  //Pular linha
        this.document.add(new Paragraph("______________________________________________________________________________"));  //Pular linha
        this.document.add(new Paragraph(" "));  //Pular linha
        
        
        // CONFIGURAÇÃO DOS DADOS DE TUTOR
        float width = 500;
        float height = document.getPageSize().getHeight();
        float columnDefinitionSize[] = {50f, 50f};
        float pos = height / 2;
        PdfPTable table = null;
        PdfPCell cellTutor = null;
        PdfPCell cellPaciente = null;
        
        FormatacaoPrescricao formTutor = retornarFormatacao(3);
        FormatacaoPrescricao formCpfTutor = retornarFormatacao(4);
        FormatacaoPrescricao formTelefoneTutor = retornarFormatacao(5);
        
        Font fonteTutor = new Font(Font.HELVETICA, formTutor.getTamanho(), getFontStyle(formTutor), Color.decode(formTutor.getCor()));
        
        table = new PdfPTable(columnDefinitionSize);
        table.getDefaultCell().setBorder(0);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setTotalWidth(width - 72);
        table.setLockedWidth(true);
        
        cellTutor = new PdfPCell();
//        cellTutor.setColspan(columnDefinitionSize.length);
        cellTutor.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellTutor.setPaddingLeft(formTutor.getRecuo());
        cellTutor.setBorder(0);
        
        String nomeTutor = "Tutor: " + prescricao.getPet().getTutorPet().getNome() + "\n";
        String cpfTutor = "CPF: " +Utils.imprimeCPF(prescricao.getPet().getTutorPet().getCpf()) + "\n";
        String telefoneTutor = "Telefone: " + Utils.imprimeTelefone(prescricao.getPet().getTutorPet().getTelefoneTutor());
        String stringCellTutor = nomeTutor;
        if (formCpfTutor.isPresente()){
            stringCellTutor += cpfTutor;
        }
        if (formTelefoneTutor.isPresente()){
            stringCellTutor += telefoneTutor;
        }
        cellTutor.addElement(new Phrase(stringCellTutor, fonteTutor));
        table.addCell(cellTutor);
        
        // CONFIGURAÇÃO DOS DADOS DE PACIENTE
        FormatacaoPrescricao formPaciente = retornarFormatacao(6);
        FormatacaoPrescricao formEspeciePaciente = retornarFormatacao(7);
        FormatacaoPrescricao formRacaPaciente = retornarFormatacao(8);
        FormatacaoPrescricao formPesoPaciente = retornarFormatacao(9);
        FormatacaoPrescricao formIdadePaciente = retornarFormatacao(21);
        FormatacaoPrescricao formMicrochipPaciente = retornarFormatacao(10);
        Font fontePaciente = new Font(Font.HELVETICA, formPaciente.getTamanho(), getFontStyle(formPaciente), Color.decode(formPaciente.getCor()));
        
        cellPaciente = new PdfPCell();
//        cellPaciente.setColspan(columnDefinitionSize.length);
        cellPaciente.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellPaciente.setPaddingLeft(formPaciente.getRecuo());
        cellPaciente.setBorder(0);
        LocalDate dtNascimentoPet = prescricao.getPet().getDataNascimentoPet();
        int idadeAnos = Period.between(dtNascimentoPet, LocalDate.now()).getYears();
        int idadeMeses = Period.between(dtNascimentoPet, LocalDate.now()).getMonths();
                
        String nomePaciente = "Paciente: " + prescricao.getPet().getNomePet() + "\n";
        String especiePaciente = "Espécie: " + prescricao.getPet().getEspecie().getNome() + "\n";
        String racaPaciente = "Raça: " + prescricao.getPet().getRaca() + "\n";
        String pesoPaciente = "Peso: "  + Utils.imprimeNumero(prescricao.getPet().getPesoPet()) + " kg\n";
        String idadePaciente = "Idade: ";
        if (idadeAnos > 0 && idadeMeses > 0){
            idadePaciente += idadeAnos + " anos, " +idadeMeses + " meses\n";
        }else if (idadeAnos > 0){
            idadePaciente += idadeAnos + " anos\n";
        }else if (idadeMeses > 0){
            idadePaciente += idadeMeses + " meses\n";
        }else{
            idadePaciente = "";
        }
        String microchipPaciente = "Nº Microchip: " + prescricao.getPet().getRfid();
        String stringCellPaciente = nomePaciente;
        if (formEspeciePaciente.isPresente()){
            stringCellPaciente += especiePaciente;
        }
        if (formRacaPaciente.isPresente()){
            stringCellPaciente += racaPaciente;
        }
        if (formPesoPaciente.isPresente()){
            stringCellPaciente += pesoPaciente;
        }
        if (formIdadePaciente.isPresente()){
            stringCellPaciente += idadePaciente;
        }
        if (formMicrochipPaciente.isPresente() && !prescricao.getPet().getRfid().equals("")){
            stringCellPaciente += microchipPaciente;
        }
        cellPaciente.addElement(new Phrase(stringCellPaciente, fontePaciente));
        table.addCell(cellPaciente);
        
        document.add(table);
        
        
        this.document.add(new Paragraph(""));  //Pular linha
        this.document.add(new Paragraph(" "));  //Pular linha
        
    }
    
    public void gerarCorpo(Prescricao prescricao){
        
        FormatacaoPrescricao formFraseInicial = retornarFormatacao(11);
        Paragraph parInicioCorpo = new Paragraph();
        parInicioCorpo.setAlignment(Element.ALIGN_JUSTIFIED);
        parInicioCorpo.setSpacingAfter(5);
        parInicioCorpo.add(
                new Chunk(
                        formFraseInicial.getTexto() + "\n",
                        new Font(Font.HELVETICA, formFraseInicial.getTamanho(), getFontStyle(formFraseInicial), Color.decode(formFraseInicial.getCor()))
                )
        );
        document.add(parInicioCorpo);
        
        FormatacaoPrescricao formFormaUso = retornarFormatacao(12);
        Font fonteFormaUso = new Font(Font.HELVETICA, formFormaUso.getTamanho(), getFontStyle(formFormaUso), Color.decode(formFormaUso.getCor()));
        FormatacaoPrescricao formProduto = retornarFormatacao(13);
        Font fonteProduto = new Font(Font.HELVETICA, formProduto.getTamanho(), getFontStyle(formProduto), Color.decode(formProduto.getCor()));
        FormatacaoPrescricao formPosologia = retornarFormatacao(14);
        Font fontePosologia = new Font(Font.HELVETICA, formPosologia.getTamanho(), getFontStyle(formPosologia), Color.decode(formPosologia.getCor()));
        
        Map<String, Map<String, String[]>> listaPrescricao =  prescricao.getListaProdutos();
        Set<String> formasDeUso = listaPrescricao.keySet();
        Set<String> listaProdutosDaFormaDeUso;
        
        for (String forma : formasDeUso){
            listaProdutosDaFormaDeUso = listaPrescricao.get(forma).keySet();
            Paragraph parFormasDeUso = new Paragraph();
            parFormasDeUso.setAlignment(Element.ALIGN_LEFT);
            parFormasDeUso.setIndentationLeft(formFormaUso.getRecuo());
            parFormasDeUso.add(
                    new Chunk(
                            forma +":",
                            fonteFormaUso
                    )
            );
            this.document.add(parFormasDeUso);
            
            for (String produto : listaProdutosDaFormaDeUso){
                String[] dados = listaPrescricao.get(forma).get(produto);
                String quantidade = dados[0];
                String posologia = dados[1];
                
                Paragraph parProduto = new Paragraph();
                Chunk chunkProduto = new Chunk(produto, fonteProduto);
                Chunk chunkQtd = new Chunk("....................................................." + quantidade + "\n");
                Chunk chunkPosologia = new Chunk(posologia, fontePosologia);
                
                parProduto.setAlignment(Element.ALIGN_LEFT);
                parProduto.setIndentationLeft(formProduto.getRecuo());
                parProduto.setSpacingAfter(10);
                parProduto.add(chunkProduto);
                parProduto.add(chunkQtd);
                parProduto.add(chunkPosologia);
                this.document.add(parProduto);
            }
        }
        
        this.document.add(new Paragraph(" "));  //Pular linha
        
        FormatacaoPrescricao formObservacoes = retornarFormatacao(15);
        Font fonteObservacoes = new Font(Font.HELVETICA, formObservacoes.getTamanho(), getFontStyle(formObservacoes), Color.decode(formObservacoes.getCor()));
        
        Paragraph parObservacoes = new Paragraph();
        parObservacoes.setAlignment(Element.ALIGN_JUSTIFIED);
        parObservacoes.setIndentationLeft(formObservacoes.getRecuo());
        parObservacoes.add(
                new Chunk(
                        "\n Informações adicionais: \n" + 
                                prescricao.getObservacoes(),
                        fonteObservacoes
                )
        );
        this.document.add(parObservacoes);
    }
    
    public void gerarRodape(Prescricao prescricao, PdfWriter writer) {
        
        this.document.add(new Paragraph(" "));  //Pular linha
        this.document.add(new Paragraph(" "));  //Pular linha
        
        String traco = "";
        for (int i = 0; i < prescricao.getVeterinario().getNome().length() + 5; i++) {
            traco += "_";
        }
        
        FormatacaoPrescricao formVeterinario = retornarFormatacao(20);
        Font fonteVeterinario = new Font(Font.HELVETICA, formVeterinario.getTamanho(), getFontStyle(formVeterinario), Color.decode(formVeterinario.getCor()));
        
        Paragraph parVeterinario = new Paragraph();
        parVeterinario.setAlignment(formVeterinario.getAlinhamento());
        if (formVeterinario.getAlinhamento() == 0){
            parVeterinario.setIndentationLeft(formVeterinario.getRecuo());
        }else if (formVeterinario.getAlinhamento() == 2){
            parVeterinario.setIndentationRight(formVeterinario.getRecuo());
        }
        parVeterinario.setSpacingBefore(formVeterinario.getEspacamento());
        parVeterinario.add(
                new Chunk(
                        traco + "\n",
                        fonteVeterinario
                )
        );
        
        String prefixo;
        if (prescricao.getVeterinario().isSexo()){
            prefixo = "Dr. ";
        }else{
            prefixo = "Dra. ";
        }
        
        parVeterinario.add(
                new Chunk(
                        prefixo + prescricao.getVeterinario().getNome() + "\n",
                        fonteVeterinario
                )
        );
        parVeterinario.add(
                new Chunk(
                        "CRMV   " + prescricao.getVeterinario().getCrmv(),
                        fonteVeterinario
                )
        );
        document.add(parVeterinario);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String data = prescricao.getData().format(formatter);
        String municipio = prescricao.getClinica().getMunicipioClinica().getNome();
        
        FormatacaoPrescricao formRodapeData = retornarFormatacao(16);
        Font fonteRodape = new Font(Font.TIMES_ROMAN, formRodapeData.getTamanho(), getFontStyle(formRodapeData), Color.decode(formRodapeData.getCor()));
        
        Paragraph parData = new Paragraph();
        parData.setAlignment(Element.ALIGN_CENTER);
        parData.add(
                new Chunk(
                        municipio + ", " + data + ".",
                        fonteRodape
                )
        );

        // step 3: create a footer
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(500);
        table.setWidths(new int[]{500});
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

//        Paragraph title =  new Paragraph("Footer", new Font(Font.HELVETICA, 10));
        PdfPCell dataCell = new PdfPCell(parData);
        dataCell.setPaddingTop(1);
        dataCell.setPaddingBottom(5);
        dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        dataCell.setBorder(Rectangle.BOTTOM);
        dataCell.setBorderColor(Color.decode(formRodapeData.getCor()));
        table.addCell(dataCell);

        Paragraph parDados;
        PdfPCell dadosCell = new PdfPCell();
        dadosCell.setPaddingTop(2);
        dadosCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        dadosCell.setBorder(Rectangle.NO_BORDER);
        
        FormatacaoPrescricao formRodapeEmail = retornarFormatacao(17);
        FormatacaoPrescricao formRodapeEndereco = retornarFormatacao(18);
        FormatacaoPrescricao formRodapeTelefone = retornarFormatacao(19);
        
        String rodapeEmail = prescricao.getClinica().getEmailClinica() + "\n";
        String rodapeEndereco = prescricao.getClinica().getRuaClinica() + ", " + prescricao.getClinica().getNumeroClinica() + 
                ", " + prescricao.getClinica().getMunicipioClinica().getNome() + 
                " - " + prescricao.getClinica().getMunicipioClinica().getEstado() + "\n";
        String rodapeTelefone; 
        if (prescricao.getClinica().getTelefoneAlternativoClinica() != null && !prescricao.getClinica().getTelefoneAlternativoClinica().equals("")){
            rodapeTelefone = "Fones " + Utils.imprimeTelefone(prescricao.getClinica().getTelefoneClinica()) + " e " + Utils.imprimeTelefone(prescricao.getClinica().getTelefoneAlternativoClinica());
        } else {
            rodapeTelefone = "Fone " + Utils.imprimeTelefone(prescricao.getClinica().getTelefoneClinica());
        }
        String dadosRodape = "";
        if (formRodapeEmail.isPresente()){
            dadosRodape += rodapeEmail;
        }
        if (formRodapeEndereco.isPresente()){
            dadosRodape += rodapeEndereco;
        }
        if (formRodapeTelefone.isPresente()){
            dadosRodape += rodapeTelefone;
        }
        
        parDados = new Paragraph(dadosRodape, fonteRodape);
        parDados.setAlignment(Element.ALIGN_CENTER);
        dadosCell.addElement(parDados);
        
        table.addCell(dadosCell);

        table.writeSelectedRows(0, -1, 34, 100, writer.getDirectContent());
    }
    
    public void imprimir(Prescricao prescricao){
        if (this.document != null && this.document.isOpen()){
            document.close();
        }
    }
    
    /*   Números relacionados aos campos a serem formatados:
            1-Nome da clinica
            2-Descrição da clinica (junto ao nome)
            3-Nome do Tutor (A formatação se extende a todos os dados do tutor)
            4-CPF do Tutor
            5-Telefone do Tutor
            6-Nome do Paciente (A formatação se extende a todos os dados do paciente)
            7-Espécie do Paciente
            8-Raça do Paciente
            9-Peso do Paciente
            10-Microchip do Paciente
            11-Frase inicial do corpo da prescrição
            12-Forma de uso dos medicamentos prescritos
            13-Produtos e quantidades prescritos
            14-Posologia
            15-Observações da prescrição
            16-Data do rodapé (A formatação se extende a todo o rodapé)
            17-Email do rodapé
            18-Endereço do rodapé
            19-Telefone do rodapé
    */
    
    private FormatacaoPrescricao retornarFormatacao(int item) {
        // Esse método recebe o numero do item a ser formatado como parâmetro, e 
        // devolve um objeto de formatação com os atributos a serem inseridos na 
        // formatação, conforme a formatação padrão selecionada na tela de preferências.
        
        for (FormatacaoPrescricao form : listaFormatacoes) {
            if (form.getItem() == item) {
                return form;
            }
        }
        return null;
    }
    
    private int getFontStyle(FormatacaoPrescricao form){
        if (form.isNegrito() && form.isItalico()){
            return 3;
        }else if (form.isNegrito() && !form.isItalico()){
            return 1;
        }else if(!form.isNegrito() && form.isItalico()){
            return 2;
        }else{
            return 0;
        }
    }
}
