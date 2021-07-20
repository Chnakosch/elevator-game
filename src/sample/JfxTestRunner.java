package sample;

import java.util.concurrent.CountDownLatch;

import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.sun.javafx.application.PlatformImpl;


@SuppressWarnings( "restriction" )
public class JfxTestRunner extends BlockJUnit4ClassRunner
{

    public JfxTestRunner( final Class<?> klass ) throws InitializationError
    {
        super( klass );
        try
        {
            setupJavaFX();
        }
        catch ( final InterruptedException e )
        {
            throw new InitializationError( "Could not initialize the JavaFx platform." );
        }
    }

    private static void setupJavaFX() throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch( 1 );

        PlatformImpl.startup( () ->
        {
        } );

        latch.countDown();

        latch.await();
    }
}