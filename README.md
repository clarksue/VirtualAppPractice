# About

This project is some practices.

In order to study virtual App, We do following Practices.

# 1. DeepInVirtualAppP1

https://github.com/dodola/DeepInVirtualApp

As this project is too old and can't run directly in latest android studio.

I transferred it to a Project can run under API 28. 

Following are the key points to make it run.
- How to call hidden apis
   
   please refer to https://github.com/anggrayudi/android-hidden-api
   
- How to access private method

   We can using java reflection. https://stackoverflow.com/questions/880365/any-way-to-invoke-a-private-method 

- Some errors i occurred.
    - Failed to transform MockableJarTransform. 
    
        I handled this by downgrade gradle version to 3.1.4. https://github.com/anggrayudi/android-hidden-api/issues/46
        
    - Create new project and my android studio 3.5.3 can't sync correctly.
    
        Mentioned in the above article, downgrade gradle version to 3.1.1 can sync correctly.
       
# 2. VirtualAppP1 (not finish yet)

https://github.com/asLody/VirtualApp

This practice is much more difficult than before. The above project has complete functions to run virtual app.

It is too old to run in Android studio 3.5.3 with API 28. This practice is to transfer the project to run with API 28.

