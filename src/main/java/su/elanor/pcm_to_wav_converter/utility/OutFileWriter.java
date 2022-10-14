package su.elanor.pcm_to_wav_converter.utility;

import su.elanor.pcm_to_wav_converter.exceptions.FileChannelNotOpenedException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class OutFileWriter {
    private FileChannel channel;
    private int totalBytesWritten = 0;

    public OutFileWriter() {}

    public void openChannel(String pathToSave) throws FileNotFoundException
    {
        totalBytesWritten = 0;
        channel = new FileOutputStream(pathToSave).getChannel();
    }

    public void closeChannel() throws IOException
    {
        if ( channel != null ) {
            channel.close();
            channel = null;
        }
    }

    public void writeToChannel(ByteBuffer bufferToWrite) throws IOException
    {
        if ( channel == null ) {
            throw new FileChannelNotOpenedException("");
        }

        while( bufferToWrite.hasRemaining() ) {
            int bytesWritten = channel.write(bufferToWrite);
            if ( bytesWritten <= 0 ) {
                break;
            }
        }

        totalBytesWritten += bufferToWrite.capacity();
    }

    public void truncateFile( int fileSize )
    {
        if ( channel != null ) {
            try {
                channel.truncate(fileSize);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getTotalBytesWritten() {
        return totalBytesWritten;
    }
}
