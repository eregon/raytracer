require 'erb'

scenes = {
  5 => %w[cone.obj cube.obj cylinder.obj plane.obj sphere.obj torus.obj teapot.obj],
  10 => %w[bunny.obj treebranches.obj.gz treeleaves.obj.gz triceratops.obj venus.obj],
  15 => %w[tablecloth.obj table.obj]
}

sdl_file = 'XML/tmp.sdl'
sdl_template = ERB.new(DATA.read)

scenes.each_pair { |distance, objs|
  objs.each { |obj|
    next if obj.end_with? '.gz'
    pos = ([distance]*3)*' '
    sdl = sdl_template.result(binding)
    File.write sdl_file, sdl
    out = 'output/' + File.basename(obj, File.extname(obj)) + '.png'
    puts "#{obj} => #{out}"
    system 'java', '-cp', 'bin', 'raytracer.CLI', sdl_file, out
  }
}

__END__
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE Sdl SYSTEM "sdl.dtd">

<Sdl>
   <Cameras>
      <Camera position="<%= pos %>" direction="-0.5 -0.5 -0.5" up="0 1 0" fovy="45" name="cam0" />
   </Cameras>

   <Lights>
      <PointLight position="<%= pos %>" intensity="1" color="1 1 1" name="light0" />
   </Lights>

   <Geometry>
      <FileGeometry filename="<%= obj %>" name="subject" />
   </Geometry>

   <Materials>
      <DiffuseMaterial color="1 1 1" name="white" />
   </Materials>

   <Scene camera="cam0" lights="light0" background="0 0 0">
      <Shape geometry="subject" material="white" />
   </Scene>
</Sdl>
