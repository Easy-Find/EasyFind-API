package com.be.two.c.apibetwoc.service.imagem;


import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.service.arquivo.IStorage;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.imagem.mapper.ImagemMapper;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
@RequiredArgsConstructor
public class ImagemService {

    private final IStorage arquivoService;


    public Imagem cadastrarImagensProduto(MultipartFile file, TipoArquivo tipoArquivo, Produto produto, PilhaObj<ArquivoSaveDTO> arquivos){
        ArquivoSaveDTO arquivo= arquivoService.salvarArquivo(file,tipoArquivo);
        return ImagemMapper.of(arquivo,produto);
    }

    public Imagem cadastrarImagensEstabelecimento(MultipartFile file, TipoArquivo tipoArquivo, Estabelecimento estabelecimento, PilhaObj<ArquivoSaveDTO> arquivos){
        ArquivoSaveDTO arquivo= arquivoService.salvarArquivo(file,tipoArquivo);
        return ImagemMapper.of(arquivo,estabelecimento);
    }
}
