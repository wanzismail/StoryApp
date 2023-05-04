/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

object AppConfiguration {
    const val applicationId = "id.wanztudio.storyapp"

    const val minSdk = 21
    const val targetSdk = 32
    const val compileSdk = 32

    const val versionCode = 1

    private const val majorVersion = 1
    private const val minorVersion = 0
    private const val patchVersion = 0
    const val versionName = "$majorVersion.$minorVersion.$patchVersion"

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}