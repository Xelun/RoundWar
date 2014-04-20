package ProfileSettings;

import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class ProfileSerializer {
    // the location of the profile data file
    private static final String PROFILE_DATA_FILE = ".roundWar/profile.dat";
    private static Profile profile;

    /**
     * Read the player's profile, creating one if needed.
     */
    public static Profile read() {
        Gdx.app.log( RoundWar.LOG, "Reading profile" );

        // if the profile is already loaded, just return it
        if( profile != null ) return profile;

        // create the handle for the profile data file
        FileHandle profileDataFile = Gdx.files.external( PROFILE_DATA_FILE );

        // create the JSON utility object
        Json json = new Json();

        // check if the profile data file exists
        if( profileDataFile.exists() ) {
            // load the profile from the data file
            try {
                // read the file as text
                String profileAsCode = profileDataFile.readString();

                // decode the contents
                String profileAsText = Base64Coder.decodeString( profileAsCode );
                //System.out.println(profileAsText);
                // restore the state
                profile = json.fromJson( Profile.class, profileAsText );
                //profile = json.fromJson( Profile.class, profileAsCode );

            } catch( Exception e ) {
                // log the exception
                Gdx.app.error( RoundWar.LOG, "Unable to parse existing profile data file", e );

                // recover by creating a fresh new profile data file;
                // note that the player will lose all game progress
                profile = new Profile();
                write( profile );
            }
        } else {
            // create a new profile data file
            profile = new Profile();
            write( profile );
        }
        // return the result
        return profile;
    }

    /**
     * Save the given profile.
     */
    public static void write( Profile profile ) {
        Gdx.app.log( RoundWar.LOG, "Saving profile" );

        // create the JSON utility object
        Json json = new Json();

        // create the handle for the profile data file
        FileHandle profileDataFile = Gdx.files.external( PROFILE_DATA_FILE );

        // convert the given profile to text
        String profileAsText = json.toJson( profile );

        // encode the text
        String profileAsCode = Base64Coder.encodeString( profileAsText );

        // write the profile data file
        profileDataFile.writeString( profileAsCode, false );
        //profileDataFile.writeString( profileAsText, false );
    }

    /**
     * Save the player's profile.
     * If no profile is available, this method does nothing.
     */
    public void write() {
        if( profile != null ) {
            write( profile );
        }
    }
}