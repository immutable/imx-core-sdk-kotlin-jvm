import fileinput
import sys

filename = './imx-android-sdk/build/generated/src/main/kotlin/org/openapitools/client/infrastructure/ApiClient.kt'
for i, line in enumerate(fileinput.input(filename, inplace=1)):
    sys.stdout.write(line.replace('builder.build()',
                                  'builder.addInterceptor {\n\t\t\t\tit.proceed(\n\t\t\t\t\tit.request().newBuilder()\n\t\t\t\t\t\t.addHeader("x-sdk-version", "imx-android-sdk-v{}")\n\t\t\t\t\t\t.build()\n\t\t\t\t)\n\t\t\t}.build()'.replace(
                                      "{}", sys.argv[1])))
