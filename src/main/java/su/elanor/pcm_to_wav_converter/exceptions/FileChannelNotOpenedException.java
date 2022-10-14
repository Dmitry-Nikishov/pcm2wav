package su.elanor.pcm_to_wav_converter.exceptions;

public class FileChannelNotOpenedException extends RuntimeException{
    public FileChannelNotOpenedException( String msg )
    {
        super( msg );
    }
}
